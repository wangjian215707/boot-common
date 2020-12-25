package com.github.edu.security.login.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.security.login.entity.TSysRoleUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
public interface ISysRoleUserDao extends CustomRepository<TSysRoleUser,Long> {

    /**
     * 根据用户角色删除对应的岗位成员信息
     * @param roleId
     * @return
     */
    @Modifying
    @Query("delete from TSysRoleUser t where t.roleId =:roleId")
    int deleteAllByRoleId(@Param("roleId")Long roleId);
}
