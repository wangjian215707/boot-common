package com.github.edu.security.login.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 记录用户登陆信息
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/18
 */
@Data
@Entity
@Table(name = "t_sys_login")
public class TSysLogin implements Serializable {

    @Id
    private Long id;

    private String userId;//用户登陆账号

    private String msg;//错误信息

    private Long sfjj;//0:未解决；1：已解决

    private String loginTime;//登录异常时间

    private String loginIp;//异常登陆ip地址


}
