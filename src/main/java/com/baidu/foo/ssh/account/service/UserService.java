package com.baidu.foo.ssh.account.service;

import com.baidu.foo.ssh.account.bo.SysUser;

public interface UserService {

	SysUser findUserByUsername(String username);
}
