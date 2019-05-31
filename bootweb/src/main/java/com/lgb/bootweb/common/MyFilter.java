package com.lgb.bootweb.common;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 自定义的拦截器
 */
public class MyFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyFilter.class);
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        LOGGER.info("this is MyFilter,url:" + request.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse); //放行
    }
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }
    @Override
    public void destroy() {
    }
}
