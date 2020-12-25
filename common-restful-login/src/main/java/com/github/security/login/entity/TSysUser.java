package com.github.security.login.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 系统用户表，
 * 保存用户账号，密码，启用状态，登录信息
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/8/29
 */
@Data
@Entity
@Table(name = "t_sys_user")
public class TSysUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysUser")
    private Long id;

    private String userId;//用户登陆账号

    private String username;//用户中午姓名

    private String password;//用户登陆密码

    private Long yhlx;//用户类型

    private String yhlxmc;//用户类型名称

    private String lastLoginTime;//最后登陆时间

    private Long dlcs;//登陆次数

    private String lastLoginIp;//最后登陆ip地址

    private Long isLocked = 0L ;//是否锁定

    private String lockedName="否";//账号是否被禁用

    private Long isRemove =0L;//是否被移除

    private Long isEnable=1L;//是否启用

    private String enableName="启用";//账号启用


}
