package com.baidu.foo.ssh.account.dao;

import java.util.List;

import com.baidu.foo.ssh.account.bo.SysRole;

public interface RoleDao {
	
	public List<SysRole> findRolesByUserId(Long userId);

}
