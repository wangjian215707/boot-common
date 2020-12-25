package com.github.edu.security.login.service;

import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.client.common.service.BaseService;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/24
 * Time: 13:53
 */
public interface TSysUserService extends BaseService<TSysUser,Long> {

    TSysUser getUser(String userId);
}
