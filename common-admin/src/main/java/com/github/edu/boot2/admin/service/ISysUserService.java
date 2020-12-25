package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysUser;
import com.github.edu.client.common.service.BaseService;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/24
 * Time: 13:53
 */
public interface ISysUserService extends BaseService<TSysUser,Long> {

    TSysUser getUser(String userId);

    TSysUser getSecurityUser(String userId);
}
