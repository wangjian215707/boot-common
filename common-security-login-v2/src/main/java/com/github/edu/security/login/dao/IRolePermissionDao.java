package com.github.edu.security.login.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.security.login.entity.TSysRolePermission;

import java.util.List;

/**
 * 角色资源管理
 * Create by IntelliJ IDEA
 *
 * 用户：王建
 *
 * 日期：2019/8/29
 *
 */
public interface IRolePermissionDao extends CustomRepository<TSysRolePermission,Long> {

}
