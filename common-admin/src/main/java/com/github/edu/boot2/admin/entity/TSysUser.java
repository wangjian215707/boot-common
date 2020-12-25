package com.github.edu.boot2.admin.entity;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/12
 */
@ApiModel
@Entity
@Table(name = "T_SYS_USER")
@Data
public class TSysUser implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysUser")
    private Long id;//主键

    private String name;//用户姓名

    private String password;//密码

    private String loginIp;//最后登陆IP地址

    private String userId;//登陆账号

    private Integer isLocked;//是否锁定

    private String lastLoginTime;//最后登陆时间

    private Long dlcs;//登陆次数

    private Integer zt;//用户状态

    private Integer yhsx;//用户属性

    private Integer yhsf;//用户身份

    public TSysUser(){

    }

    public TSysUser(Long id,String name,String userId,Integer yhsf,Integer yhsx,Long dlcs){
        this.id=id;
        this.name=name;
        this.userId=userId;
        this.yhsf=yhsf;
        this.yhsx=yhsx;
        this.dlcs=dlcs;
    }

}
