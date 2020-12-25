package com.github.edu.security.login.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-12-12
 */
@Data
public class TSysWxUser implements Serializable {

    private String openid;
    private String image;
    private String userid;
    private String pwd;
    private String cookie;
    private String name;
}
