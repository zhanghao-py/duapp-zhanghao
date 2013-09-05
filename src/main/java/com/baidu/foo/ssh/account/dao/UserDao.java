package com.baidu.foo.ssh.account.dao;

import com.baidu.foo.ssh.account.bo.SysUser;

public interface UserDao {

	SysUser findUserByUsername(String username);

}
