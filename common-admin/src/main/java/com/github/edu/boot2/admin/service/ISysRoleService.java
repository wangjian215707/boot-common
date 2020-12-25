package com.github.edu.boot2.admin.service;

import com.github.edu.boot2.admin.entity.TSysRole;
import com.github.edu.client.common.service.BaseService;

import java.util.List;

/**
 * 角色管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/22
 */
public interface ISysRoleService extends BaseService<TSysRole,Long> {

    /**
     * 查询某个菜单所需要的权限
     * @param menuId
     * @return
     */
    List<TSysRole> queryAllByMenuId(Long menuId);

    /**
     * 查询某个接口所需要的权限
     * @param functionId
     * @return
     */
    List<TSysRole> queryAllByFunctionId(Long functionId);


    /**
     * 根据用户登陆账号，查询用户具有的角色
     * @param userId
     * @return
     */
    List<TSysRole> queryAllByUserId(String userId);

}
