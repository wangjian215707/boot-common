package com.github.security.login.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.security.login.entity.TSysPermission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
public interface IPermissionDao extends CustomRepository<TSysPermission,Long> {

    /**
     * 查询用户具有哪些菜单的权限
     * （非管理员，普通用户）
     * @param uid
     * @return
     */
    @Query("select t from TSysPermission t where exists (select s.permissionId from TSysRolePermission s " +
            " where s.permissionId=t.id and exists " +
            "(select m.roleId from TSysRoleUser m where  m.roleId =s.roleId and m.userId =:uid and exists (" +
            "select n.id from TSysRole n where n.id=m.roleId and n.isRemove =0 and n.isEnable = 1 ))) and t.isRemove =0 and " +
            "t.isEnable =1 and (t.type = 1 or t.type =2 ) order by t.px asc")
    List<TSysPermission> getAllByUserId(@Param("uid") String uid);

    /**
     * 查询全部菜单信息
     * （管理员权限）
     * @return
     */
    @Query("select t from TSysPermission t where t.isEnable = 1 and (t.type=1 or t.type=2 ) order by t.px asc ")
    List<TSysPermission> getAllByUserId();


}
