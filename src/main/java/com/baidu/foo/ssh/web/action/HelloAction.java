package com.baidu.foo.ssh.web.action;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.baidu.foo.ssh.service.HelloService;

@Path("hello")
public class HelloAction {
	
	private Logger log = Logger.getLogger(HelloAction.class);
	
	private HelloService helloService;

	@GET
	@Path("sayHello")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public String sayHello(@QueryParam("username") String username) {
		try {
			helloService.sayHello("zhanghao333");
		} catch (Exception e) {
			log.warn(e);
		}
		return "Hello " + username;
	}
	
	public void setHelloService(HelloService helloService) {
		this.helloService = helloService;
	}
}
