package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysUser;

/**
 * 用户查询，用于Spring Security
 *  登陆模块
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/13
 */
public interface ISysUserLoginService {

    TSysUser getUser(String userId);
}
