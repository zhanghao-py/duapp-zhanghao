package com.baidu.foo.ssh.service.impl;

import com.baidu.foo.ssh.bo.Role;
import com.baidu.foo.ssh.dao.RoleDao;
import com.baidu.foo.ssh.service.HelloService;

public class HelloServiceImpl implements HelloService {

	private RoleDao roleDao;
	
	@Override
	public String sayHello(String username) throws Exception {
		
		Role role = new Role();
		role.setName(username);
		roleDao.addRole(role);
		
		return role.getName();
	}
	
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}

}
