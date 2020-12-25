package com.github.edu.boot2.admin.security.jwt;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/27
 */
@Data
public class JwtUserInfo implements Serializable,JwtUserInformation {

    private String id;

    private String name;

    private String password;

    public JwtUserInfo(String id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtUserInfo jwtInfo = (JwtUserInfo) o;
        return Objects.equals(id, jwtInfo.id) &&
                Objects.equals(name, jwtInfo.name);
    }

    @Override
    public String username() {
        return name;
    }

    @Override
    public String userId() {
        return password;
    }

}
