package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysOnlineUser;
import com.github.edu.client.common.service.BaseService;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
public interface ISysUserOnlineService extends BaseService<TSysOnlineUser,Long> {

    /**
     * 删除非当前登陆用户
     * @param userId
     * @param token
     * @return
     */
    int deleteOnlineUser(String userId,String token);
}
