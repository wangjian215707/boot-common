package com.github.edu.security.login.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * 系统资源表，
 * 保存系统资源信息（包含目录，菜单，方法）
 * 注意：拥有菜单权限后，默认拥有了查询列表的权限
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/8/29
 */
@Data
@Entity
@Table(name = "t_sys_permission")
public class TSysPermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysPermission")
    private Long id;

    private String code;//资源编码

    private String name;//资源名称

    private String nickname;//中文名称

    private Long pid;//父节点编号

    private String pcode;//父节点编码

    private String pname;//父节点名称

    private String pnickname;//父节点中文名称

    private Long type;//资源类型 1、文件夹；2、菜单；3、按钮

    private String typeName;//资源类型名称

    private String icon;//资源图标

    private String path;//地址

    private String objName;//操对象名称

    private String createTime;//创建时间

    private String proposer;//创建人

    private Long builtIn;//是否内置（系统默认资源）

    private Long isRemove=0L;//是否被移除

    private String removeName;//是否被删除名称

    private Long px;//排序

    private String dictionary;//描述

    private Long isEnable;//是否启用

    private String enableName;//启用状态名称

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "t_sys_role_permission",joinColumns = {@JoinColumn(name = "permissionId")},
    inverseJoinColumns = {@JoinColumn(name = "roleId")})
    @JsonIgnore
    private List<TSysRole> roles;//角色集合


}
