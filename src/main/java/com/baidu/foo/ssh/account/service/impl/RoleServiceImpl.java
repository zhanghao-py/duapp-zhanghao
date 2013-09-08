package com.baidu.foo.ssh.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.baidu.foo.ssh.account.bo.SysRole;
import com.baidu.foo.ssh.account.bo.SysUser;
import com.baidu.foo.ssh.account.bo.SysUserRole;
import com.baidu.foo.ssh.account.dao.RoleDao;
import com.baidu.foo.ssh.account.dao.UserRoleDao;
import com.baidu.foo.ssh.account.service.RoleService;

@Component("roleService")
public class RoleServiceImpl implements RoleService {
	
	@Resource(name = "roleDao")
	private RoleDao roleDao;
	
	@Resource(name = "userRoleDao")
	private UserRoleDao userRoleDao;

	@Override
	public List<SysRole> findRolesByUser(SysUser user) {
		Assert.notNull(user.getId(), "userId can't be null.");
		return roleDao.findRolesByUserId(user.getId());
	}

	@Override
	public void addRole4User(String roleName, SysUser user) {
		Assert.notNull(user.getId(), "userId can't be null.");
		Assert.notNull(roleName, "roleName can't be null.");
		
		SysRole role = this.findRoleByName(roleName);
		
		Assert.notNull(role, "roleName=" + roleName + " can't be null.");
		
		Long userId = user.getId();
		Long roleId = role.getId();
		
		SysUserRole userRole = new SysUserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		userRoleDao.save(userRole);
		
		return;
	}

	@Override
	public SysRole findRoleByName(String roleName) {
		Assert.notNull(roleName, "roleName can't be null.");
		return roleDao.findRoleByName(roleName);
	}

}
