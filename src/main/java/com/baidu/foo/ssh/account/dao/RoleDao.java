package com.baidu.foo.ssh.account.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baidu.foo.ssh.account.bo.SysRole;

public interface RoleDao {
	
	public List<SysRole> findRolesByUserId(@Param("userId") Long userId);

	public SysRole findRoleByName(@Param("roleName") String roleName);

}
