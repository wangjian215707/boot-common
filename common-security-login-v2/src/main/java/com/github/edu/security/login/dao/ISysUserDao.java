package com.github.edu.security.login.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.security.login.entity.TSysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
public interface ISysUserDao extends CustomRepository<TSysUser,Long> {

    @Query("select t from TSysUser t where t.userId=:userId and t.isEnable = 1 ")
    List<TSysUser> getAllByUserid(@Param("userId")String userId);
}
