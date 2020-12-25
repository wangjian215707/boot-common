package com.github.edu.security.login.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Intellij IDE
 * User wangjian
 * Date 2017/11/14
 * Time 12:52
 * Version V1.01
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
