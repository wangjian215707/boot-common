package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysSystem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 *  接入系统管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/16
 */
public interface ISysSystemDao extends CustomRepository<TSysSystem,Long> {


    /**
     * 根据系统编码查询接入系统相关信息
     * @param code
     * @param zt
     * @return
     */
    @Query("select t from TSysSystem t where t.code=:code and t.state =:zt and t.yzfs =:yzfs")
    List<TSysSystem> queryByCode(@Param("code")String code,
                                 @Param("zt")Integer zt,
                                 @Param("yzfs")Integer yzfs);

}
