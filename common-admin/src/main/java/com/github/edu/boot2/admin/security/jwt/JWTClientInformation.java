package com.github.edu.boot2.admin.security.jwt;

/**
 * 客户端基本信息
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/14
 * Time: 21:58
 */
public interface JWTClientInformation {

    String getClientId();//客户端编号

    String getClientNAME();//客户端名称

}
