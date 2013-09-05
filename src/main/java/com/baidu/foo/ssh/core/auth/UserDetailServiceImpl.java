package com.baidu.foo.ssh.core.auth;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.baidu.foo.ssh.account.bo.SysRole;
import com.baidu.foo.ssh.account.bo.SysUser;
import com.baidu.foo.ssh.account.service.RoleService;
import com.baidu.foo.ssh.account.service.UserService;

public class UserDetailServiceImpl implements UserDetailsService {

	private static Logger log = Logger.getLogger(UserDetailsService.class);
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Resource(name = "roleService")
	private RoleService roleService;
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		log.info("username is " + username);

		SysUser user = userService.findUserByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(user);  
        
        boolean enables = true;  
        boolean accountNonExpired = true;  
        boolean credentialsNonExpired = true;  
        boolean accountNonLocked = true;
        
        return new User(user.getUsername(), user.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);  
	}
	
	//取得用户的权限  
    private Set<GrantedAuthority> obtionGrantedAuthorities(SysUser user) {  
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>(); 
        List<SysRole> roles = roleService.findRolesByUser(user);
          
        for(SysRole role : roles) {
        	authSet.add(new SimpleGrantedAuthority(role.getName()));  
        }
        
        return authSet;  
    }

}
