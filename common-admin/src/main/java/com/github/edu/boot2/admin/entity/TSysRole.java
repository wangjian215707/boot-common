package com.github.edu.boot2.admin.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统角色表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
@ApiModel
@Entity
@Table(name = "T_SYS_ROLE")
@Data
public class TSysRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysRole")
    private Long id;

    private String name;//角色名称

    private String code;//角色编码

    private  Integer rtype;//角色类型 ：系统角色，部门角色

    private Long px;//排序

    private Integer zt;//状态

    private String description;//描述
}
