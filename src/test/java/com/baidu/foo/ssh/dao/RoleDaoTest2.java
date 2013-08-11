package com.baidu.foo.ssh.dao;

import java.util.List;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.baidu.foo.ssh.bo.Role;
import com.baidu.foo.util.SpringTransactionalTestCase;

public class RoleDaoTest2 extends SpringTransactionalTestCase {
	
	@Autowired
	private RoleDao roleDao;

	@Test
	public void itShouldLoadAllRoles() {
		List<Role> roles = roleDao.loadAllRoles();
		
		Assert.assertThat(roleDao, Matchers.notNullValue());
//		Assert.assertThat(roles, Matchers.notNullValue());
	}
	
	@Test
	@Rollback(true)
	public void itShouldAddRole() {
		Role role = new Role();
		role.setName("mybaidu6");
		roleDao.addRole(role);
	}
}
