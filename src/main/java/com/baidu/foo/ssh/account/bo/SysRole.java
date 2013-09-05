package com.baidu.foo.ssh.account.bo;

import java.io.Serializable;

public class SysRole implements Serializable {
	
	private static final long serialVersionUID = 2619123694997850172L;
	
	private Long id;
	private String name;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

}
