package com.github.edu.security.login.service;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

/**
 * 权限校验
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
public interface IAuthService {

    /**
     * 权限校验
     * @param request
     * @param authentication
     * @return
     */
    boolean isAccess(HttpServletRequest request, Authentication authentication);
}
