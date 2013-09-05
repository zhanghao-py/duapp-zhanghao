package com.baidu.foo.ssh.core.auth;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

public class UserAuthFilter extends AbstractSecurityInterceptor implements Filter {

	private static Logger log = Logger.getLogger(UserAuthFilter.class);
	
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	@Override
	public Class<?> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	@Override
	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return securityMetadataSource;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		invoke(fi);
	}
	
	private void invoke(FilterInvocation fi) throws IOException, ServletException {
		/**
		 * 1.获取请求资源的权限  
         * 执行Collection<ConfigAttribute> attributes = SecurityMetadataSource.getAttributes(object);  
         * 2.是否拥有权限  
         * this.accessDecisionManager.decide(authenticated, object, attributes);  
         */
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try { 
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally { 
			super.afterInvocation(token, null);
		}
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		
	}
	
	@Override
	public void destroy() {
		
	}

	public void setSecurityMetadataSource(
			FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}
	

	/*
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
			chain.doFilter(request, response);

		} else {

			Baidu baidu = null;

			try {
				baidu = new Baidu(clientId, clientSecret, redirectUri, store,
						req);
				String state = baidu.getState();
				Map<String, String> params = new HashMap<String, String>();
				params.put("state", state);
				String authorizeUrl = baidu.getBaiduOAuth2Service()
						.getAuthorizeUrl(params);
				res.sendRedirect(authorizeUrl);
			} catch (BaiduOAuthException e) {
				log.warn("BaiduOAuthException ", e);
			} catch (BaiduApiException e) {
				log.warn("BaiduApiException ", e);
			}
		}

	}
	*/


}
