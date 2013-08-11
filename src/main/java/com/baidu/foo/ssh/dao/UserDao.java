package com.baidu.foo.ssh.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baidu.foo.ssh.bo.User;
import com.baidu.foo.ssh.core.mybatis.pagination.PageBean;

public interface UserDao {

	public List<User> loadAllUsers(@Param("page") PageBean page);

}
