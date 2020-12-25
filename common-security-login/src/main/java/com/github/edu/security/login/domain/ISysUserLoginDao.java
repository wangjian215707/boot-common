package com.github.edu.security.login.domain;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.security.login.entity.TSysLogin;

/**
 * 用户登陆日志
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/18
 */
public interface ISysUserLoginDao extends CustomRepository<TSysLogin,Long> {
}
