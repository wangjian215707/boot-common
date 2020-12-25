package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysUserToken;
import com.github.edu.client.common.service.BaseService;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/21
 */
public interface ISysUserTokenService extends BaseService<TSysUserToken,Long> {

    TSysUserToken getUserToken(String cid,String uid);
}
