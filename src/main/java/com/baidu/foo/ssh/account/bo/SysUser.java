package com.baidu.foo.ssh.account.bo;

import java.io.Serializable;

public class SysUser implements Serializable {

	private static final long serialVersionUID = -8138447347176260275L;

	private Long id;
	private String username;
	private String password;
	private Short enabled;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Short getEnabled() {
		return enabled;
	}
	
	public void setEnabled(Short enabled) {
		this.enabled = enabled;
	}
}