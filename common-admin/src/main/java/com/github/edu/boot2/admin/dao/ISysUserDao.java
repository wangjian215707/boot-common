package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
public interface ISysUserDao extends CustomRepository<TSysUser,Long> {

    @Query("select t from TSysUser t where t.userId=:userId and t.isLocked =:locked and t.zt=:zt")
    List<TSysUser> getAllByUserid(@Param("userId") String userId,@Param("locked")Integer locked,@Param("zt")Integer zt);

    @Query("select t from TSysUser t where t.userId=:userId and t.isLocked =:locked and t.zt=:zt")
    List<TSysUser> getAllInformationByUserid(@Param("userId") String userId,@Param("locked")Integer locked,@Param("zt")Integer zt);
}
