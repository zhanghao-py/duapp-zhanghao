package com.baidu.foo.ssh.core.auth;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.domain.User;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;
import com.baidu.foo.ssh.account.bo.SysUser;
import com.baidu.foo.ssh.account.service.UserService;
import com.baidu.foo.ssh.core.config.SystemConfig;

public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	private static Logger log = Logger.getLogger(LoginFilter.class);
	
	@Resource(name = "userService")
	private UserService userService;
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request,
			HttpServletResponse response) throws AuthenticationException {
		
		String clientId = SystemConfig.CLIENTID;
		String clientSecret = SystemConfig.CLIENTSECRET;
		String redirectUri = buildRedirectUrl(request);
		
		BaiduStore store = new BaiduCookieStore(clientId, request, response);
		
		User user = null;
		
		try {
			Baidu baidu = new Baidu(clientId, clientSecret, redirectUri, store, request);
			user = baidu.getLoggedInUser();
		} catch (BaiduOAuthException e) {
			log.warn("BaiduOAuthException ", e);
		} catch (BaiduApiException e) {
			log.warn("BaiduApiException ", e);
		}
		
		if (user == null) {
			throw new AuthenticationServiceException("user has not logged in!");
		}
		
        String username = user.getUname();
        String password = "";
		
        SysUser sysUser = userService.findUserByUsername(username);
        
        if(sysUser == null) {
        	throw new AuthenticationServiceException("username is not found!");
        }
        
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
        setDetails(request, authRequest); 
		return this.getAuthenticationManager().authenticate(authRequest);
	}
	
	private String buildRedirectUrl(HttpServletRequest request) {
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(request.getScheme());
		sb.append("://");
		sb.append(request.getServerName());
		sb.append(":");
		sb.append(request.getServerPort());
		
		String contextPath = request.getContextPath();
		if (!StringUtils.isBlank(contextPath)) {
			sb.append(contextPath);
		}
		
		sb.append(SystemConfig.REDIRECTURI.toString());
		
		return sb.toString();
	}
	
	

}
