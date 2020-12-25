package com.github.edu.bsdt.jwt.client.service.impl;

import com.github.edu.bsdt.jwt.client.service.JWTClientInformation;

import java.io.Serializable;
import java.util.Objects;

/**
 * JWT客户端基本信息
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/14
 * Time: 22:01
 */
public class JWTInfo implements Serializable, JWTClientInformation {

    private String id;

    private String name;


    public  JWTInfo(String id,String name){
        this.id=id;
        this.name=name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JWTInfo jwtInfo = (JWTInfo) o;
        return Objects.equals(id, jwtInfo.id) &&
                Objects.equals(name, jwtInfo.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

    @Override
    public String getClientId() {
        return id;
    }

    @Override
    public String getClientNAME() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
