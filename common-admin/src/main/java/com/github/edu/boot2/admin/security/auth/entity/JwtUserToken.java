package com.github.edu.boot2.admin.security.auth.entity;

import com.github.edu.boot2.admin.entity.TSysUser;
import lombok.Data;

import java.io.Serializable;

/**
 * 返回给前端的用户基本信息
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
@Data
public class JwtUserToken extends ErrorMessage implements Serializable {

    private String token;//令牌

    private Integer expiresIn;//令牌有效期

    private TSysUser user;//用户账号
}
