package com.baidu.foo.ssh.account.bo;

import java.io.Serializable;

public class SysUserRole implements Serializable {

	private static final long serialVersionUID = 6150167955415663700L;

	private Long id;
	private Long userId;
	private Long roleId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

}
