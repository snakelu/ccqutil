package com.lyh.ccqutil.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class LoginFilter extends HandlerInterceptorAdapter {
	private Logger logger = LoggerFactory.getLogger(LoginFilter.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String authorization = request.getHeader("Authorization");
		logger.info("The authorization is: {}", authorization);
		return super.preHandle(request, response, handler);
	}
}