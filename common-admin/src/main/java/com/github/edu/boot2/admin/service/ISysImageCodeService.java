package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysImageCode;
import com.github.edu.client.common.service.BaseService;

/**
 * 验证码保存，及校验功能
 * 验证码保存
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
public interface ISysImageCodeService extends BaseService<TSysImageCode,Long> {

    TSysImageCode queryByUUID(String uuid);
}
