package com.github.edu.security.login.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018/3/21
 * 在登录之前记住访问地址及请求参数，在登录成功之后再取到这个地址然后回跳到对应的地址。
 * 首先我们需要写一个过滤器来获取我们的请求地址，并放到Session中。
 */
public class HttpParamsFilter implements Filter {

    public static String REQUESTED_URL = "CasRequestedUrl";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {
        final HttpServletRequest request = (HttpServletRequest) servletRequest;
        final HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession();
        String requestPath = request.getServletPath();
        session.setAttribute(REQUESTED_URL, requestPath);
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
