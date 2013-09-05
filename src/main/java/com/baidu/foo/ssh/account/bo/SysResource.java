package com.baidu.foo.ssh.account.bo;

import java.io.Serializable;

public class SysResource implements Serializable {

	private static final long serialVersionUID = -7075248157604234052L;

	private Long id;
	private String url;
	private String auth;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

}
