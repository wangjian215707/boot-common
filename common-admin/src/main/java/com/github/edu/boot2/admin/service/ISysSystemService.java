package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysSystem;
import com.github.edu.client.common.service.BaseService;

/**
 * 接入系统管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/16
 */
public interface ISysSystemService extends BaseService<TSysSystem,Long> {

    /**
     * 根据编码查询接入系统信息
     * @param code
     * @return
     */
    TSysSystem getTSysStem(String code,Integer yzfs);

}
