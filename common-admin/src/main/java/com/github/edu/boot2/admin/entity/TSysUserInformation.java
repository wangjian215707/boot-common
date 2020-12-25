package com.github.edu.boot2.admin.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户基本信息表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
@ApiModel
@Entity
@Table(name = "T_SYS_USER_INFORMATION")
@Data
public class TSysUserInformation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysUserInformation")
    private Long id;

    private String userId;//用户登陆账号，关联用户表userId

    private String name;//用户姓名

    private String email;//电子邮箱

    private String phone;//电话号码

    @Lob
    private String uhead;//用户头像 base64

    private String weixin;//微信号

    private Long zjlx;//证件类型

    private String zjh;//证件号



}
