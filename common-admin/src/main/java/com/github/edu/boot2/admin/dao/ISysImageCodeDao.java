package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysImageCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 验证码保存功能
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
public interface ISysImageCodeDao extends CustomRepository<TSysImageCode,Long> {


    @Query("select t from TSysImageCode t where t.uuid =:uid")
    List<TSysImageCode> getAllByUuid(@Param("uid")String uid);
}
