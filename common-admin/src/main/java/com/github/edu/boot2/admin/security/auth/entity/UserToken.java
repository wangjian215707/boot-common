package com.github.edu.boot2.admin.security.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 *  用户登录token
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/20
 */
@Data
public class UserToken extends ErrorMessage implements Serializable {

    private String token;

    private Integer expiresIn;//令牌有效期


}
