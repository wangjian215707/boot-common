package com.github.edu.security.login.company.entity;

/**
 * AccessToken 对象
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-9-27
 */
public class AccessTokenEntity {

    private Integer errcode;//错误代码

    private String errmsg;//错误信息

    private String access_token;

    private Integer expires_in;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Integer getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }
}
