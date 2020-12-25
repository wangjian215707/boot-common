package com.github.security.login.service;

/**
 * 用户权限及资源相关接口
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
public interface IUserPermissionService {

    /**
     * 获取用户菜单权限
     * @param userId
     * @return
     */
    String userMenu(String userId);

    /**
     * 获取用户按钮权限
     * @param userId
     * @return
     */
    String userButton(String userId);

    /**
     * 保存或者更新用户菜单权限
     * @param obj
     * @return
     */
    String saveOrUpdateUserMenu(String obj);

    /**
     * 删除菜单权限
     * @param id
     * @return
     */
    String delUserMenu(Long id);

    /**
     * 保存或者更新用户按钮权限
     * @param obj
     * @return
     */
    String saveOrUpdateUserButton(String obj);

    /**
     * 删除按钮权限
     * @param id
     * @return
     */
    String delUserButton(Long id);
}
