package com.github.edu.security.login.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 岗位成员表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/8/29
 */
@Data
@Entity
@Table(name = "t_sys_role_user")
public class TSysRoleUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysRoleUser")
    private Long id;

    private Long roleId;

    private String roleName;

    private String userId;//用户账号

    private String username;//用户名称

    private Long orgId;//部门ID

    private String orgName;//部门名称

    private String createTime;//创建时间

    private String proposer;//创建人
}
