package com.baidu.foo.ssh.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.baidu.foo.ssh.account.bo.SysRole;
import com.baidu.foo.ssh.account.bo.SysUser;
import com.baidu.foo.ssh.account.dao.RoleDao;
import com.baidu.foo.ssh.account.service.RoleService;

@Component("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Resource(name = "roleDao")
	private RoleDao roleDao;

	@Override
	public List<SysRole> findRolesByUser(SysUser user) {
		Assert.notNull(user.getId(), "userId can't be null.");
		return roleDao.findRolesByUserId(user.getId());
	}

}
