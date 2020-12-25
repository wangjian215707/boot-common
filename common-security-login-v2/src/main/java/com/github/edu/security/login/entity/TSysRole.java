package com.github.edu.security.login.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统角色表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/8/29
 */
@Data
@Entity
@Table(name = "t_sys_role")
public class TSysRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysRole")
    private Long id;

    private String createTime;//创建时间

    private Long isRemove=0L;//是否被移除

    private String removeName;//是否被删除名称

    private String name;//角色名称(英文名称)

    private String nickname;//角色中文名称

    private String dictionary;//角色描述

    private Long builtIn =0L;//是否为内置角色

    private String proposer;//角色创建人

    private Long isEnable;//是否启用

    private String ztmc;//状态名称

    @Transient
    private Long pid;//虚拟列

    /**
     * Spring Security 4.0以上版本角色都默认以'ROLE_'开头
     * @param name
     */
    public void setName(String name) {
        if (name.indexOf("ROLE_") == -1) {
            this.name = "ROLE_" + name;
        } else {
            this.name = name;
        }
    }

}
