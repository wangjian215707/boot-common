package com.github.edu.security.login.service;

import com.github.edu.security.login.entity.TSysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-4-16
 */
public interface UserService {

    boolean checkedLogin(HttpServletRequest request, HttpServletResponse response);

    boolean openIdLogin(HttpServletRequest request, HttpServletResponse response,String openId);

    TSysUser getUser(String userId);
}
