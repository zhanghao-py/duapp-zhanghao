package com.baidu.foo.ssh.core.auth;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.access.intercept.RequestKey;
import org.springframework.stereotype.Component;

import com.baidu.foo.ssh.account.bo.SysResource;
import com.baidu.foo.ssh.account.service.ResourceService;

@Component("securityRequestMapFactoryBean")
public class SecurityRequestMapFactoryBean extends LinkedHashMap<RequestKey, Collection<ConfigAttribute>> {

	private static final long serialVersionUID = 419426335127107964L;
	
	@Resource(name = "resourceService")
	private ResourceService resourceService;
	
	@PostConstruct
	private void loadResourceDefine() {
//		resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
		List<SysResource> resources = resourceService.findAllResources();

		for (SysResource resource : resources) {
			RequestKey requestKey = new RequestKey(resource.getUrl());
			List<ConfigAttribute> configAttributes = SecurityConfig
					.createListFromCommaDelimitedString(resource.getAuth());
			this.put(requestKey, configAttributes);
		}
	}

}
