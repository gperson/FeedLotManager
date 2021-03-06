package com.holz.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;


public class LoginFailureHandler implements AuthenticationFailureHandler {

	private static final Logger logger = Logger.getLogger(LoginFailureHandler.class);
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request,	HttpServletResponse response, AuthenticationException exception)
			throws IOException, ServletException {
		logger.error("Failed login", exception);
		response.sendRedirect("/login?error");
	}
	
}
