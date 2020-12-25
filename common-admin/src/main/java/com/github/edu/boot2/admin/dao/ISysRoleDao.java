package com.github.edu.boot2.admin.dao;

import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.edu.boot2.admin.entity.TSysRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 角色管理表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/20
 */
public interface ISysRoleDao extends CustomRepository<TSysRole,Long> {

    /**
     * 查询某个菜单所需要的角色信息
     * @param menuId
     * @param zt
     * @return
     */
    @Query("select t from TSysRole t where exists (select s.roleId from TSysRoleMenu s where s.roleId =t.id and s.menuId =:menuId) and t.zt =:zt")
    List<TSysRole> queryAllByIdForMenu(@Param("menuId")Long menuId,@Param("zt")Integer zt);

    /**
     * 查询某个菜单下面的所有方法需要的角色信息
     * @param menuId
     * @param zt
     * @return
     */
    @Query("select t from TSysRole t where exists (select s.roleId from TSysRoleFunction s where s.roleId=t.id " +
            "and exists (select f.id from TSysMenuFunction f where f.id = s.menuFunctionId and f.menuId =:menuId))and t.zt =:zt")
    List<TSysRole> queryAllByIdForFunction(@Param("menuId")Long menuId,@Param("zt")Integer zt);

    /**
     * 查询接口所需要的角色信息
     * @param functionId 接口id
     * @param zt
     * @return
     */
    @Query("select t from TSysRole t where exists (select s.roleId from TSysRoleFunction s where s.roleId =t.id and s.functionId =:functionId) and t.zt=:zt")
    List<TSysRole> queryAllByIdForApi(@Param("functionId")Long functionId,@Param("zt")Integer zt);

    /**
     * 根据用户编号查询用户具有的角色
     * @param zt
     * @return
     */
    @Query("select t from TSysRole t where exists (select s.roleId from TSysRoleUser s where s.roleId=t.id and s.userId=:userId) and t.zt=:zt")
    List<TSysRole> queryAllByUserId(@Param("zt")Integer zt,@Param("userId")String UserId);
}
