package com.baidu.foo.ssh.dao;

import java.util.List;

import com.baidu.foo.ssh.bo.Role;

public interface RoleDao {
	
	public List<Role> loadAllRoles();
	public int addRole(Role role);

}
