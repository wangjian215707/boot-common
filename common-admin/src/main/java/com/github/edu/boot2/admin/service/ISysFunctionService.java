package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysFunction;

import java.util.List;

/**
 * 方法及接口管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/22
 */
public interface ISysFunctionService {

    /**
     * 查询全部接口信息
     * @return
     */
    List<TSysFunction> queryAllApi();

    List<TSysFunction> queryAllApiNow();
}
