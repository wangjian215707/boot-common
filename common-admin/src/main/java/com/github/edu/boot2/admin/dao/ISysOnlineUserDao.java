package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysOnlineUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
public interface ISysOnlineUserDao extends CustomRepository<TSysOnlineUser,Long> {

    @Transactional
    @Modifying
    @Query("delete from TSysOnlineUser t where t.userId =:uid and t.token <>:token")
    int deleteAllByUserIdAndToken(@Param("uid")String uid,@Param("token")String token);
}
