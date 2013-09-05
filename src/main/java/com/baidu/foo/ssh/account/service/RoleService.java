package com.baidu.foo.ssh.account.service;

import java.util.List;

import com.baidu.foo.ssh.account.bo.SysRole;
import com.baidu.foo.ssh.account.bo.SysUser;

public interface RoleService {

	List<SysRole> findRolesByUser(SysUser user);
}
