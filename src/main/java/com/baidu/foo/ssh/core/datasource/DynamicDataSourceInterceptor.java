package com.baidu.foo.ssh.core.datasource;

import java.util.Map;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.log4j.Logger;
import org.springframework.util.PatternMatchUtils;

public class DynamicDataSourceInterceptor implements MethodInterceptor {
	private final Logger log = Logger.getLogger(DynamicDataSourceInterceptor.class);

	private DataSourceKeyHolder dataSourceKeyHolder;

	private String[] randomReadMethods;

	private Map<String, String> specialReadMethods;

	public Object invoke(MethodInvocation invocation) throws Throwable {
		String method = invocation.getMethod().getName();
		String specialReadKey = getSpecialReadKey(method);
		if (specialReadKey != null) {
			dataSourceKeyHolder.setKey(specialReadKey);
		} else if (isRandomReadMethod(method)) {
			dataSourceKeyHolder.setReadKeyRandomly();
		} else {
			dataSourceKeyHolder.setWriteKey();
		}
		log.info("the current method is " + method + ", and datasource is " + dataSourceKeyHolder.getKey());
		return invocation.proceed();
	}

	private String getSpecialReadKey(String methodName) {
		if (specialReadMethods == null || specialReadMethods.isEmpty()) {
			return null;
		}
		String bestMatched = null;
		String dateSourceKey = null;
		for (Map.Entry<String, String> entry : specialReadMethods.entrySet()) {
			if (PatternMatchUtils.simpleMatch(entry.getKey(), methodName)) {
				if (bestMatched == null || bestMatched.length() < entry.getKey().length()) {
					bestMatched = entry.getKey();
					dateSourceKey = entry.getValue();
				}
			}
		}
		return dateSourceKey;
	}

	private boolean isRandomReadMethod(String methodName) {
		if (randomReadMethods == null || randomReadMethods.length == 0) {
			return false;
		}
		return PatternMatchUtils.simpleMatch(randomReadMethods, methodName);
	}

	public DataSourceKeyHolder getDataSourceKeyHolder() {
		return dataSourceKeyHolder;
	}

	public void setDataSourceKeyHolder(DataSourceKeyHolder dataSourceKeyHolder) {
		this.dataSourceKeyHolder = dataSourceKeyHolder;
	}

	public String[] getRandomReadMethods() {
		return randomReadMethods;
	}

	public void setRandomReadMethods(String[] randomReadMethods) {
		this.randomReadMethods = randomReadMethods;
	}

	public Map<String, String> getSpecialReadMethods() {
		return specialReadMethods;
	}

	public void setSpecialReadMethods(Map<String, String> specialReadMethods) {
		this.specialReadMethods = specialReadMethods;
	}
}
