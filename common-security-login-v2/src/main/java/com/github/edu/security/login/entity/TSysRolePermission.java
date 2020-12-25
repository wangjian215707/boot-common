package com.github.edu.security.login.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 角色资源表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/8/29
 */
@Data
@Entity
@Table(name = "t_sys_role_permission")
public class TSysRolePermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysRolePermission")
    private Long id;

    private Long permissionId;//资源编码

    private String permissionName;//资源名称

    private Long roleId;//角色id

    private String roleName;//角色名称

    private Long authType;//授权类型

    private String typeName;//授权类型 1，菜单授权，2，按钮授权

}
