package com.baidu.foo.ssh.account.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.baidu.foo.ssh.account.bo.SysResource;
import com.baidu.foo.ssh.account.dao.ResourceDao;
import com.baidu.foo.ssh.account.service.ResourceService;

@Component("resourceService")
public class ResourceServiceImpl implements ResourceService {

	@Resource(name = "resourceDao")
	private ResourceDao resourceDao;
	
	@Override
	public List<SysResource> findAllResources() {
		return resourceDao.findAllResources();
	}

}
