package com.github.edu.security.login.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-8-1
 */
@Data
public class TSysUser implements Serializable {

    private Long id;
    private String userid;
    private String lasttime;
    private String locked;
    private String loginip;
    private Long loginnum;
    private Long orgid;
    private String password;
    private Long state;
    private String name;
    private Long yhsf;
    private String orgname;

    public TSysUser(){

    }

    public TSysUser(Long id, String userid, String name) {
        this.id = id;
        this.userid = userid;
        this.name = name;
    }
}
