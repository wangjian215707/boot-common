package com.github.edu.security.login.entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-8-1
 */
@ApiModel
@Entity
@Table(name = "T_SYS_USER")
public class TSysUser implements Serializable {

    private Long id;
    @ApiModelProperty("登录账号")
    private String userid;
    @ApiModelProperty("最后登录时间")
    private String lasttime;
    @ApiModelProperty("账号是锁定状态")
    private String locked;
    @ApiModelProperty("登录地址")
    private String loginip;
    @ApiModelProperty("登录次数")
    private Long loginnum;
    @ApiModelProperty("组织机构编号")
    private Long orgid;
    @ApiModelProperty("密码")
    private String password;
    @ApiModelProperty("用户状态")
    private Long state;
    @ApiModelProperty("用户名称")
    private String name;
    @ApiModelProperty("用户身份")
    private Long yhsf;
    @ApiModelProperty("组织机构名称")
    private String orgname;


    public TSysUser(){

    }

    public TSysUser(Long id, String userid, String name) {
        this.id = id;
        this.userid = userid;
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,generator = "TableGenerator")
    @TableGenerator(name = "TableGenerator",table = "t_sequence",
            pkColumnName = "name",
            valueColumnName = "id",
            pkColumnValue = "TSysUser")
    @Column(name = "id",nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "userid",nullable = false,length = 64)
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "lasttime",length = 64)
    public String getLasttime() {
        return lasttime;
    }

    public void setLasttime(String lasttime) {
        this.lasttime = lasttime;
    }

    @Basic
    @Column(name = "locked",length = 50)
    public String getLocked() {
        return locked;
    }

    public void setLocked(String locked) {
        this.locked = locked;
    }

    @Basic
    @Column(name = "loginip",length =50)
    public String getLoginip() {
        return loginip;
    }

    public void setLoginip(String loginip) {
        this.loginip = loginip;
    }

    @Basic
    @Column(name = "loginnum")
    public Long getLoginnum() {
        return loginnum;
    }

    public void setLoginnum(Long loginnum) {
        this.loginnum = loginnum;
    }

    @Basic
    @Column(name = "orgid")
    public Long getOrgid() {
        return orgid;
    }

    public void setOrgid(Long orgid) {
        this.orgid = orgid;
    }

    @Basic
    @Column(name = "password",length = 64)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "state")
    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    @Basic
    @Column(name = "name",length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "yhsf")
    public Long getYhsf() {
        return yhsf;
    }

    public void setYhsf(Long yhsf) {
        this.yhsf = yhsf;
    }

    @Basic
    @Column(name = "orgname",length = 128)
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
