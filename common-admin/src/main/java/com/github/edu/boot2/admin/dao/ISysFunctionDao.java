package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysFunction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 菜单方法及接口管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/22
 */
public interface ISysFunctionDao extends CustomRepository<TSysFunction,Long> {

    /**
     * 查询接口
     * @param rm 是否删除
     * @param zt 当前状态
     * @param lx 类型
     * @return
     */
    @Query("select t from TSysFunction t where t.isRemove =:rm and t.zt=:zt and t.lx=:lx order by t.px desc ")
    List<TSysFunction> queryAllByApi(@Param("rm")Integer rm,@Param("zt")Integer zt,@Param("lx")Integer lx);
}
