package com.github.admin.edu.assembly.jwt.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/15
 * Time: 22:02
 */
public class JwtTokenEntity implements Serializable {

    private Integer state;//JwtToken 状态

    private String msg; //异常信息

    private String token;//客户端token

    private Integer age;//有效期

    public JwtTokenEntity(){
        this.state=200;
        this.msg="";
    }

    public JwtTokenEntity(String msg){
        this.state=4001;
        this.msg=msg;
    }
    public JwtTokenEntity(String msg,Integer state){
        this.state=state;
        this.msg=msg;
    }


    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JwtTokenEntity that = (JwtTokenEntity) o;
        return Objects.equals(state, that.state) &&
                Objects.equals(msg, that.msg) &&
                Objects.equals(token, that.token) &&
                Objects.equals(age, that.age);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, msg, token, age);
    }
}
