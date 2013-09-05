package com.baidu.foo.ssh.account.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.baidu.foo.ssh.account.bo.SysUser;
import com.baidu.foo.ssh.account.dao.UserDao;
import com.baidu.foo.ssh.account.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	private UserDao userDao;
	
	@Override
	public SysUser findUserByUsername(String username) {
		Assert.notNull(username, "username can't be null.");
		return userDao.findUserByUsername(username);
	}

	
}
