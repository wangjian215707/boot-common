package com.github.edu.security.login.entity;

import org.springframework.security.core.GrantedAuthority;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-10-8
 */
public class CasTgc implements GrantedAuthority {

    private String tgc;

    public CasTgc(String tgc){
        this.tgc=tgc;
    }
    public void setTgc(String tgc) {
        this.tgc = tgc;
    }

    @Override
    public String getAuthority() {
        return tgc;
    }
}
