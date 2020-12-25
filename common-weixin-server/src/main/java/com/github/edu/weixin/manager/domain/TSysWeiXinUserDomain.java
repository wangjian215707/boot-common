package com.github.edu.weixin.manager.domain;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.weixin.manager.entity.TSysWxUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TSysWeiXinUserDomain extends CustomRepository<TSysWxUser,String> {

       @Query("select t from TSysWxUser t where t.userid=:userId")
    List<TSysWxUser> getAllByUserid(@Param("userId")String userId);

    @Query("select t from TSysWxUser t where t.cookie =:cookie")
    List<TSysWxUser> getAllByCookie(@Param("cookie")String cookie);
}
