package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysMenu;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/20
 */
public interface ISysMenuDao extends CustomRepository<TSysMenu,Long> {


    @Query("select t from TSysMenu t where t.zt=:zt and t.isRemove =:remove and t.type = 2 order by t.px desc ")
    List<TSysMenu> queryAllByZtAndIsRemove(@Param("zt")Integer zt,@Param("remove")Integer remove);
}
