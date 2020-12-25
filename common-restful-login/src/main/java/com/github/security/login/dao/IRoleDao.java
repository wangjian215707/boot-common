package com.github.security.login.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.security.login.entity.TSysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
public interface IRoleDao extends CustomRepository<TSysRole,Long> {


    /**
     * 查询用户资源权限
     * @param path
     * @return
     */
    @Query("select t from TSysRole t left join TSysRolePermission s " +
            "on t.id=s.roleId left join TSysPermission d on d.id= s.permissionId where " +
            "t.isRemove = 0 and t.isEnable = 1 and d.isRemove = 0 and d.isEnable = 1 and d.path=:path ")
    List<TSysRole> getAllByPermissionPath(@Param("path") String path);

    /**
     * 根据登录用户获取角色信息
     * @param userId
     * @return
     */
    @Query("select t from TSysRole t left join TSysRoleUser s on t.id=s.roleId " +
            "where t.isEnable = 1 and t.isRemove = 0 and s.userId =:userId ")
    List<TSysRole> getAllByRoleUser(@Param("userId") String userId);

    /**
     * 检查用户是否具有管理员权限
     * @param userId
     * @return
     */
    @Query("select t from TSysRole t left join TSysRoleUser s on t.id=s.roleId where t.isEnable = 1 and t.isRemove = 0 " +
            "and s.userId =:userId and t.name = 'admin' ")
    List<TSysRole> checkRoleUser(@Param("userId") String userId);
}
