package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysOrganization;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

/**
 * 部门管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/6/9
 */
public interface ISysDeptManagerDao extends CustomRepository<TSysOrganization,Long> {

    /**
     * 删除部门，及部门下面的子部门
     * @param pcode
     * @param code
     * @return
     */
    @Transactional
    @Modifying
    @Query("delete from TSysOrganization t where t.code=:code or t.code like :pcode ")
    int deleteAllById(@Param("code")String code,@Param("pcode")String pcode);
}
