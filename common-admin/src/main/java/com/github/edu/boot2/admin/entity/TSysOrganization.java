package com.github.edu.boot2.admin.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 组织机构表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
@ApiModel
@Entity
@Table(name = "T_SYS_ORGANIZATION")
@Data
public class TSysOrganization implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysOrganization")
    private Long id;

    private String name;//名称

    private String code;//编码

    private Long pid;//父节点id

    private String pName;//父节点名称

    private String pCode;//父节点编号

    private Long type;//类型

    private String typeName;//类型名称

    private Long px;//排序

    private Long zt;//状态
}
