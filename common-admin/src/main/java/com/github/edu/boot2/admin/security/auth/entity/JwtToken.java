package com.github.edu.boot2.admin.security.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 返回接口token
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/14
 */
@Data
public class JwtToken extends ErrorMessage implements Serializable {

    private String accessToken;//令牌

    private Integer expiresIn;//令牌有效期
}
