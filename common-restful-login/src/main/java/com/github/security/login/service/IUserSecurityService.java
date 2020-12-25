package com.github.security.login.service;

/**
 * 用户相关
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
public interface IUserSecurityService {

    /**
     * 保存用户基本信息
     * @param obj
     * @return
     */
    String saveOrUpdateUser(String obj);

    /**
     * 删除用户
     * @param userId 登陆账号
     * @return
     */
    String delUser(String userId);

    /**
     * 查询用户基本信息
     * @param userId
     * @return
     */
    String queryUser(String userId);
}
