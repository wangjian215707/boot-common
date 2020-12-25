package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysUserToken;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/21
 */
public interface ISysUserTokenDao extends CustomRepository<TSysUserToken,Long> {

    /**
     * 查询有效token信息
     * @param cid 客户端编号
     * @param uid 用户账号
     * @param stm 当前时间
     * @return
     */
    @Query("select t from TSysUserToken t where t.clientId =:cid and t.userId =:uid and t.stateTime <=:stm and t.endTime >=:stm")
    List<TSysUserToken> queryAllByClientIdAndAndUserId(@Param("cid")String cid,@Param("uid")String uid,@Param("stm")Long stm);
}
