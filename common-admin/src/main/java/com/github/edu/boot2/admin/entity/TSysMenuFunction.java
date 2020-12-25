package com.github.edu.boot2.admin.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统菜单功能关系表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
@ApiModel
@Entity
@Table(name = "T_SYS_MENU_FUNCTION")
@Data
public class TSysMenuFunction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysMenuFunction")
    private Long id;//主键

    private Long menuId;//菜单id

    private String menuName;//菜单名称

    private Long functionId;//功能id

    private String functionName;//功能名称

    private String icon;//自定义图标

    private Integer isLock;//是否锁定

    private Long px;//排序
}
