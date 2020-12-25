package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysMenu;
import com.github.edu.client.common.service.BaseService;

import java.util.List;

/**
 * 系统菜单管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/20
 */
public interface ISysMenuService extends BaseService<TSysMenu,Long> {

    /**
     * 权限校验使用
     * @return
     */
    List<TSysMenu> getAllMenuToSecurityDataSource();

    /**
     * 获取最新的菜单信息
     * @return
     */
    List<TSysMenu> getAllMenuToSecurityDataSourceNow();
}
