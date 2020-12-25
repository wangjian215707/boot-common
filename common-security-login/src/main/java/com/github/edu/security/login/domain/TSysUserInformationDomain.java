package com.github.edu.security.login.domain;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.security.login.entity.TSysUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 用户基本信息查询
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/24
 * Time: 13:09
 */
public interface TSysUserInformationDomain extends CustomRepository<TSysUser,Long> {

    @Query("select t from TSysUser t where t.userid=:userId and t.state = 1 ")
    List<TSysUser> getAllByUserid(@Param("userId")String userId);

}
