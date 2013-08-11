package com.baidu.foo.ssh.core.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	private DataSourceKeyHolder dataSourceKeyHolder;

	@Override
	protected Object determineCurrentLookupKey() {
		return dataSourceKeyHolder.getKey();
	}

	public DataSourceKeyHolder getDataSourceKeyHolder() {
		return dataSourceKeyHolder;
	}

	public void setDataSourceKeyHolder(DataSourceKeyHolder dataSourceKeyHolder) {
		this.dataSourceKeyHolder = dataSourceKeyHolder;
	}
}
