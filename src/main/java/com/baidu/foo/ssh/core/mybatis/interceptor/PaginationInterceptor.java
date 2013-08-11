package com.baidu.foo.ssh.core.mybatis.interceptor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.builder.xml.dynamic.ForEachSqlNode;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.apache.log4j.Logger;

import com.baidu.foo.ssh.core.mybatis.dialect.Dialect;
import com.baidu.foo.ssh.core.mybatis.dialect.MySQL5Dialect;
import com.baidu.foo.ssh.core.mybatis.pagination.PageBean;

/**
 * 
 * @author zhanghao10@baidu.com
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PaginationInterceptor implements Interceptor {

	private Logger log = Logger.getLogger(PaginationInterceptor.class);
	private Dialect dialect = new MySQL5Dialect();

	@Override
	public Object intercept(Invocation invocation) throws Throwable {

		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		BoundSql boundSql = statementHandler.getBoundSql();
		MetaObject metaStatementHandler = MetaObject
				.forObject(statementHandler);
		Object parameterObject = boundSql.getParameterObject();

		if (parameterObject == null) {
			return invocation.proceed();
		}

		Object p = null;

		if (parameterObject instanceof Map) {
			@SuppressWarnings("unchecked")
			Map<String, Object> map = (Map<String, Object>) parameterObject;
			p = map.get("page");
		}

		if (p == null || !(p instanceof PageBean)) {
			return invocation.proceed();
		}

		@SuppressWarnings("unchecked")
		PageBean<Object> page = (PageBean<Object>) p;

		int count = getCountRecord(statementHandler, metaStatementHandler);

		if (log.isDebugEnabled()) {
			log.debug("Record Count is " + count);
		}

		page.setTotalRecord(count);
		page.doPaging();

		RowBounds rowBounds = new RowBounds(page.getPageSize()
				* (page.getCurrentPage() - 1), page.getPageSize());

		String originalSql = boundSql.getSql().trim();
		metaStatementHandler.setValue("delegate.boundSql.sql", dialect
				.getPageSQL(originalSql, rowBounds.getOffset(),
						rowBounds.getLimit()));
		metaStatementHandler.setValue("delegate.rowBounds.offset",
				RowBounds.NO_ROW_OFFSET);
		metaStatementHandler.setValue("delegate.rowBounds.limit",
				RowBounds.NO_ROW_LIMIT);

		if (log.isDebugEnabled()) {
			log.debug("Pagination SQL : " + boundSql.getSql());
		}

		return invocation.proceed();
	}

	private int getCountRecord(StatementHandler statementHandler,
			MetaObject metaStatementHandler) throws SQLException {

		Configuration conf = (Configuration) metaStatementHandler
				.getValue("delegate.configuration");
		Connection conn = conf.getEnvironment().getDataSource().getConnection();

		BoundSql boundSql = statementHandler.getBoundSql();

		String originalSql = boundSql.getSql().trim();
		String countSql = "select count(0) from (" + originalSql
				+ ") as tmp_count";

		PreparedStatement countStmt = conn.prepareStatement(countSql);
		setParameters(conf, boundSql, countStmt);

		ResultSet rs = countStmt.executeQuery();

		int countRecord = 0;

		if (rs.next()) {
			countRecord = rs.getInt(1);
		}

		rs.close();
		countStmt.close();
		conn.close();

		return countRecord;
	}

	private void setParameters(Configuration conf, BoundSql boundSql,
			PreparedStatement countStmt) throws SQLException {

		List<ParameterMapping> parameterMappings = boundSql
				.getParameterMappings();
		Object parameterObject = boundSql.getParameterObject();

		TypeHandlerRegistry typeHandlerRegistry = conf.getTypeHandlerRegistry();
		MetaObject metaObject = parameterObject == null ? null : conf
				.newMetaObject(parameterObject);

		for (int i = 0; i < parameterMappings.size(); i++) {
			ParameterMapping parameterMapping = parameterMappings.get(i);
			if (parameterMapping.getMode() != ParameterMode.OUT) {
				Object value;
				String propertyName = parameterMapping.getProperty();
				PropertyTokenizer prop = new PropertyTokenizer(propertyName);
				if (parameterObject == null) {
					value = null;
				} else if (typeHandlerRegistry.hasTypeHandler(parameterObject
						.getClass())) {
					value = parameterObject;
				} else if (boundSql.hasAdditionalParameter(propertyName)) {
					value = boundSql.getAdditionalParameter(propertyName);
				} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)
						&& boundSql.hasAdditionalParameter(prop.getName())) {
					value = boundSql.getAdditionalParameter(prop.getName());
					if (value != null) {
						value = conf.newMetaObject(value)
								.getValue(
										propertyName.substring(prop.getName()
												.length()));
					}
				} else {
					value = metaObject == null ? null : metaObject
							.getValue(propertyName);
				}
				TypeHandler typeHandler = parameterMapping.getTypeHandler();
				if (typeHandler == null) {
					throw new ExecutorException(
							"There was no TypeHandler found for parameter "
									+ propertyName + " of statement ");
				}
				typeHandler.setParameter(countStmt, i + 1, value,
						parameterMapping.getJdbcType());
			}
		}
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties arg0) {
	}

}
