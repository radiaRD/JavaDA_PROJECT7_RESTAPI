package com.nnk.springboot.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * Redirect users to login with Spring Security.
 */
public class LoginPageFilter extends GenericFilterBean {
    private static final Logger logger = LogManager.getLogger(LoginPageFilter.class);

@Override
public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
    chain.doFilter(request, response);
}
}
