package com.baidu.foo.ssh.account.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.baidu.foo.ssh.account.bo.SysRole;
import com.baidu.foo.ssh.account.bo.SysUser;
import com.baidu.foo.ssh.account.dao.UserDao;
import com.baidu.foo.ssh.account.service.RoleService;
import com.baidu.foo.ssh.account.service.UserService;

@Component("userService")
public class UserServiceImpl implements UserService {

	@Resource(name = "userDao")
	private UserDao userDao;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	@Override
	public SysUser findUserByUsername(String username) {
		Assert.notNull(username, "username can't be null.");
		return userDao.findUserByUsername(username);
	}

	@Override
	public Long save(SysUser user) {
		Assert.notNull(user, "user can't be null.");
		userDao.save(user);
		return user.getId();
	}

//	private Long update(SysUser user) {
//		Assert.notNull(user, "user can't be null.");
//		userDao.update(user);
//		return user.getId();
//	}

	@Override
	public void addUserFromPassport(Long uid, String username) {
		Assert.notNull(uid, "uid can't be null.");
		Assert.notNull(username, "username can't be null.");
		
		SysUser user = new SysUser();
		user.setId(uid);
		user.setUsername(username);
		user.setEnabled(Boolean.TRUE);
		
		this.save(user);
		roleService.addRole4User(SysRole.Role.ROLE_USER.name(), user);
		
		return;
	}

	
}
