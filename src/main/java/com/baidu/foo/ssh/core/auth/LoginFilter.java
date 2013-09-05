package com.baidu.foo.ssh.core.auth;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.baidu.foo.ssh.account.bo.SysUser;
import com.baidu.foo.ssh.account.service.UserService;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	@Resource(name = "userService")
	private UserService userService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		
		if (!request.getMethod().equals("POST")) {  
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());  
        } 
		
		String username = obtainUsername(request);  
        String password = obtainPassword(request); 
        
        SysUser user = userService.findUserByUsername(username);
        
        if(user == null || !user.getPassword().equals(DigestUtils.md5Hex(password))) {
        	throw new AuthenticationServiceException("username or passowrd is not correct!");
        }
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest); 
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	

}
