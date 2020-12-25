package com.github.security.login.service.impl;

import com.github.security.login.service.IUserPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
@Service
@Slf4j
public class UserPermissionServiceImpl implements IUserPermissionService {
    /**
     * 获取用户菜单权限
     *
     * @param userId
     * @return
     */
    @Override
    public String userMenu(String userId) {
        return null;
    }

    /**
     * 获取用户按钮权限
     *
     * @param userId
     * @return
     */
    @Override
    public String userButton(String userId) {
       return null;
    }

    /**
     * 保存或者更新用户菜单权限
     *
     * @param obj
     * @return
     */
    @Override
    public String saveOrUpdateUserMenu(String obj) {
        return null;
    }

    /**
     * 删除菜单权限
     *
     * @param id
     * @return
     */
    @Override
    public String delUserMenu(Long id) {
        return null;
    }

    /**
     * 保存或者更新用户按钮权限
     *
     * @param obj
     * @return
     */
    @Override
    public String saveOrUpdateUserButton(String obj) {
        return null;
    }

    /**
     * 删除按钮权限
     *
     * @param id
     * @return
     */
    @Override
    public String delUserButton(Long id) {
        return null;
    }
}
