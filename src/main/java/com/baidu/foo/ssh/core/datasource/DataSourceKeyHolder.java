package com.baidu.foo.ssh.core.datasource;

import java.util.Random;

import com.baidu.foo.ssh.core.exception.DaoException;

public class DataSourceKeyHolder {
	private static final ThreadLocal<String> keyHolder = new ThreadLocal<String>();

	private String writeKey;

	private String[] readKeys;

	private static final Random random = new Random();

	public String getKey() {
		String key = keyHolder.get();
		return key != null ? key : writeKey;
	}

	public void setKey(String dataSourceKey) {
		keyHolder.set(dataSourceKey);
	}

	public void setReadKeyRandomly() throws DaoException {
		if (readKeys == null || readKeys.length == 0) {
			throw new DaoException("readKeys is empty!");
		}
		if (readKeys.length == 1) {
			keyHolder.set(readKeys[0]);
		} else {
			keyHolder.set(readKeys[random.nextInt(100) % readKeys.length]);
		}
	}

	public void setWriteKey() {
		keyHolder.set(writeKey);
	}

	public String getWriteKey() {
		return writeKey;
	}

	public void setWriteKey(String writeKey) {
		this.writeKey = writeKey;
	}

	public String[] getReadKeys() {
		return readKeys;
	}

	public void setReadKeys(String[] readKeys) {
		this.readKeys = readKeys;
	}
}
