package com.baidu.foo.ssh.core.auth;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.baidu.api.Baidu;
import com.baidu.api.BaiduApiException;
import com.baidu.api.BaiduOAuthException;
import com.baidu.api.store.BaiduCookieStore;
import com.baidu.api.store.BaiduStore;
import com.baidu.foo.ssh.core.config.SystemConfig;

public class AuthenticationProcessingFilterEntryPoint implements AuthenticationEntryPoint {

	public static final String LOGIN_USER_KEY = "login_user_key";
	
	private static Logger log = Logger.getLogger(AuthenticationProcessingFilterEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
		
		String clientId = SystemConfig.CLIENTID;
		String clientSecret = SystemConfig.CLIENTSECRET;
		String redirectUri = buildRedirectUrl(request);

		BaiduStore store = new BaiduCookieStore(clientId, request, response);
			
		Baidu baidu = null;

		try {
			baidu = new Baidu(clientId, clientSecret, redirectUri, store,
					request);
			String state = baidu.getState();
			Map<String, String> params = new HashMap<String, String>();
			params.put("state", state);
			String authorizeUrl = baidu.getBaiduOAuth2Service()
					.getAuthorizeUrl(params);
			response.sendRedirect(authorizeUrl);
		} catch (BaiduOAuthException e) {
			log.warn("BaiduOAuthException ", e);
		} catch (BaiduApiException e) {
			log.warn("BaiduApiException ", e);
		}
			
		return;
		
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
