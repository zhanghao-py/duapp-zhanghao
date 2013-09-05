package com.baidu.foo.ssh.account.dao;

import java.util.List;

import com.baidu.foo.ssh.account.bo.SysResource;

public interface ResourceDao {

	List<SysResource> findAllResources();

}
