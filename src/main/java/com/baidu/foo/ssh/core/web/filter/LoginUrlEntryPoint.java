package com.baidu.foo.ssh.core.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.domain.Session;
import com.baidu.api.domain.User;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;
import com.baidu.foo.ssh.core.config.SystemConfig;

public class LoginUrlEntryPoint implements AuthenticationEntryPoint {
	
	private static Logger log = Logger.getLogger(LoginUrlEntryPoint.class);

	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		String clientId = SystemConfig.CLIENTID;
		String clientSecret = SystemConfig.CLIENTSECRET;
		String redirectUri = request.getRequestURL().toString();
		
        BaiduStore store = new BaiduCookieStore(clientId, request, response);
        Session session = store.getSession();
        
        // 当前已登录
        if (session != null) {
        	// 获取当前登陆者
        	User user = session.getUser();
        	log.info(user.getUname());
//        	chain.doFilter(request, response);
        	
        } else {
        
	        Baidu baidu = null;
	        
	        try {
	            baidu = new Baidu(clientId, clientSecret, redirectUri, store, request);
	            String state = baidu.getState();
	            Map<String, String> params = new HashMap<String, String>();
	            params.put("state", state);
	            String authorizeUrl = baidu.getBaiduOAuth2Service().getAuthorizeUrl(params);
	            response.sendRedirect(authorizeUrl);
	        } catch (BaiduOAuthException e) {
	            log.warn("BaiduOAuthException ", e);
	        } catch (BaiduApiException e) {
	            log.warn("BaiduApiException ", e);
	        }
        }
		
		
		
	}

}
