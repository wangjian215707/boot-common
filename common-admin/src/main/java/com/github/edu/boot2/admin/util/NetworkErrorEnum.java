package com.github.edu.boot2.admin.util;

/**
 * 网络异常状态码
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/16
 */
public enum NetworkErrorEnum {

    NETWORK_STATE_OK(200,"ok"),

    NETWORK_STATE_NO_CODE(1001,"参数缺失"),

    NETWORK_STATE_NO_TOKEN(1002,"未获取到token值"),

    NETWORK_STATE_TOKEN_ERROR(2001,"token失效"),

    NETWORK_STATE_SECRET(2002,"无效的密钥"),

    NETWORK_STATE_CLIENT_ERROR(3001,"无效的客户端"),

    NETWORK_STATE_USER_ERROR(4001,"用户不存在"),

    NETWORK_STATE_USER_CODE_ERROR(4002,"无效的用户的账号"),

    NETWORK_STATE_USER_PASSWORD_UID_ERROR(4003,"账号或者密码不能为空！"),

    NETWORK_STATE_USER_LOGIN_ERROR(4004,"用户名或者密码错误！"),

    NETWORK_STATE_IMAGE_CODE_NULL_ERROR(4005,"验证码不能为空！"),

    NETWORK_STATE_IMAGE_CODE_UUID_ERROR(4006,"无效的验证码！"),

    NETWORK_STATE_IMAGE_CODE_ERROR(4006,"验证码错误！"),

    ;
    NetworkErrorEnum(int num,String code){
        this.num=num;
        this.code=code;
    }

    private Integer num;
    private String code;

    public Integer getNum() {
        return num;
    }

    public String getCode() {
        return code;
    }
}
