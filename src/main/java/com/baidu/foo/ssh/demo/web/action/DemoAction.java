package com.baidu.foo.ssh.demo.web.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

import com.baidu.foo.ssh.core.web.action.GenericAction;

@Namespace("/demo")
public class DemoAction extends GenericAction {

	private static Logger log = Logger.getLogger(DemoAction.class);
	
	private static final long serialVersionUID = 2574846107073971980L;
	
	@Action(value = "demo", results = {@Result(name = "success", type = "dispatcher", location = "/index.jsp")} )
	public String demo() {
		log.info("hello");
		return SUCCESS;
	}

}
