package com.baidu.foo.ssh.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baidu.foo.ssh.bo.User;
import com.baidu.foo.ssh.core.mybatis.pagination.PageBean;
import com.baidu.foo.util.SpringTransactionalTestCase;


public class UserDaoTest3 extends SpringTransactionalTestCase {
	
	private Logger log = Logger.getLogger(UserDaoTest3.class);
	
	@Autowired
	private UserDao userDao;

	@Test
	public void itShouldLoadAllUsers() {
		PageBean page = new PageBean(10);
		page.setCurrentPage(1);
		
		List<User> user = userDao.loadAllUsers(page);
		log.info("size is " + user.size());
		Assert.assertThat(user.size(), Matchers.greaterThan(1));
	}
	
}
