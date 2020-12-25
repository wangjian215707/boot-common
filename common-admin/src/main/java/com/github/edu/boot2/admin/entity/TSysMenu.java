package com.github.edu.boot2.admin.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.models.auth.In;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统菜单表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
@ApiModel
@Entity
@Table(name = "T_SYS_MENU")
@Data
public class TSysMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysMenu")
    private Long id;//主键

    private String code;//资源编码(资源编码)

    private String name;//资源名称（英文名称）

    private String nickname;//中文名称

    private Long pid;//父节点编号

    private String pcode;//父节点编码

    private String pname;//父节点名称

    private String pnickname;//父节点中文名称

    private Long type;//资源类型 1、文件夹；2、菜单

    private String typeName;//资源类型名称

    private String icon;//资源图标

    private String path;//地址

    private String dValue;//参数，默认参数

    private String objName;//操对象名称

    private String createTime;//创建时间

    private String proposer;//创建人

    private Long builtIn;//是否内置（系统默认资源）

    private Integer zt;//状态

    private Integer isRemove=0;//是否被移除

    private String removeName;//是否被删除名称

    private Long px;//排序

    private String dictionary;//描述


}
