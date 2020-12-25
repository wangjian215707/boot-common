package com.github.edu.boot2.admin.security.auth.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
@Data
public class UserLogin {
    @NotBlank
    private String username;

    @NotBlank
    private String password;

    private String code;

    private String uuid = "";

    @Override
    public String toString() {
        return "{username=" + username  + ", password= ******}";
    }
}
