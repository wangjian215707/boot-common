package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysUser;
import com.github.edu.client.common.service.BaseService;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/27
 */
public interface ISysUserManagerService extends BaseService<TSysUser,Long> {

    /**
     * 查询用户基本信息
     * @param userId
     * @return
     */
    TSysUser getUserInformation(String userId);

    /**
     * 保存用户基本信息
     * @param code
     * @return
     */
    TSysUser saveOrUpdateObject(String code);
}
