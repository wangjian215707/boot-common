package com.github.edu.boot2.admin.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统功能表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
@ApiModel
@Entity
@Table(name = "T_SYS_FUNCTION")
@Data
public class TSysFunction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysFunction")
    private Long id;

    private String name;//功能名称(英文名称，执行controller地址匹配)

    private String nickname;//功能中文名称

    private String icon;//图标（默认图标）

    private String createTime;//创建时间

    private String proposer;//创建人

    private Long builtIn;//是否内置（系统默认资源）

    private Integer isRemove;//是否被移除

    private String removeName;//是否被删除名称

    private Integer zt;//状态

    private Long px;//排序

    private Integer lx;//类型：1：方法；2：接口

    private String path;//地址
}
