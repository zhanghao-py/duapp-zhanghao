package com.baidu.foo.ssh.service.impl;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;

import com.baidu.foo.ssh.service.HelloService;
import com.baidu.foo.util.SpringTransactionalTestCase;

public class HelloServiceTest extends SpringTransactionalTestCase {
	
	@Autowired
	private HelloService helloService;

	@Test
	@Rollback(true)
	public void itShouldAddRole() throws Exception {
		helloService.sayHello("mybaidu8");
	}
}
