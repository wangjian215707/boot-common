package com.github.security.login.service.impl;

import com.github.security.login.dao.ISysUserDao;
import com.github.security.login.exception.CustomException;
import com.github.security.login.service.IUserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用户基本信息相关操作
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
public class UserSecurityServiceImpl implements IUserSecurityService {

    @Autowired
    private ISysUserDao dao;

    /**
     * 保存用户基本信息
     *
     * @param obj
     * @return
     */
    @Override
    public String saveOrUpdateUser(String obj) {
        throw new CustomException(200,"111");
    }

    /**
     * 删除用户
     *
     * @param userId 登陆账号
     * @return
     */
    @Override
    public String delUser(String userId) {
        return null;
    }

    /**
     * 查询用户基本信息
     *
     * @param userId
     * @return
     */
    @Override
    public String queryUser(String userId) {
        return null;
    }
}
