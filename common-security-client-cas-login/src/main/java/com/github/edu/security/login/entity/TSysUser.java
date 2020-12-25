package com.github.edu.security.login.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-8-1
 */
@Data
public class TSysUser implements Serializable {

    private Long id;
    private String userid;
    private String lasttime;
    private String locked;
    private String loginip;
    private Long loginnum;
    private Long orgid;
    private String password;
    private Long state;
    private String name;
    private Long yhsf;
    private String orgname;

    public TSysUser(){

    }

    public TSysUser(Long id, String userid, String name) {
        this.id = id;
        this.userid = userid;
        this.name = name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    public Long getLoginnum() {
        return loginnum;
    }

    public void setLoginnum(Long loginnum) {
        this.loginnum = loginnum;
    }

    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getYhsf() {
        return yhsf;
    }

    public void setYhsf(Long yhsf) {
        this.yhsf = yhsf;
    }

    public String getOrgname() {
        return orgname;
    }

    public void setOrgname(String orgname) {
        this.orgname = orgname;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSysUser tSysUser = (TSysUser) o;
        return Objects.equals(id, tSysUser.id) &&
                Objects.equals(userid, tSysUser.userid) &&
                Objects.equals(lasttime, tSysUser.lasttime) &&
                Objects.equals(locked, tSysUser.locked) &&
                Objects.equals(loginip, tSysUser.loginip) &&
                Objects.equals(loginnum, tSysUser.loginnum) &&
                Objects.equals(orgid, tSysUser.orgid) &&
                Objects.equals(password, tSysUser.password) &&
                Objects.equals(state, tSysUser.state) &&
                Objects.equals(name, tSysUser.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, lasttime, locked, loginip, loginnum, orgid, password, state, name);
    }
}
