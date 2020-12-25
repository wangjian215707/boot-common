package com.github.edu.security.login.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/4
 * Time: 11:47
 */
public class Openid implements GrantedAuthority {

    private String openid;

    public Openid(String openid){
        this.openid=openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Override
    public String getAuthority() {
        return openid;
    }
}
