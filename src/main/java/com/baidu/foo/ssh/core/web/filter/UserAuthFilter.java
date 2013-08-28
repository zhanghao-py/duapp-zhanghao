package com.baidu.foo.ssh.core.web.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.domain.Session;
import com.baidu.api.domain.User;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;
import com.baidu.foo.ssh.core.config.SystemConfig;

public class UserAuthFilter implements Filter {

	private static Logger log = Logger.getLogger(UserAuthFilter.class);
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		String clientId = SystemConfig.CLIENTID;
		String clientSecret = SystemConfig.CLIENTSECRET;
		String redirectUri = req.getRequestURL().toString();
		
        BaiduStore store = new BaiduCookieStore(clientId, req, res);
        Session session = store.getSession();
        
        // 当前已登录
        if (session != null) {
        	// 获取当前登陆者
        	User user = session.getUser();
        	log.info(user.getUname());
        	
        } else {
        
	        Baidu baidu = null;
	        
	        try {
	            baidu = new Baidu(clientId, clientSecret, redirectUri, store, req);
	            String state = baidu.getState();
	            Map<String, String> params = new HashMap<String, String>();
	            params.put("state", state);
	            String authorizeUrl = baidu.getBaiduOAuth2Service().getAuthorizeUrl(params);
	            res.sendRedirect(authorizeUrl);
	        } catch (BaiduOAuthException e) {
	            log.debug("BaiduOAuthException ", e);
	        } catch (BaiduApiException e) {
	            log.debug("BaiduApiException ", e);
	        }
        }
        
	}

	@Override
	public void destroy() {
		
	}
	
	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}

}
