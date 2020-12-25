package com.github.admin.edu.assembly.common.enm;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/12/18
 */
public enum ErrorEnum {

    ERROR_SERVER(1001,"服务端异常！"),

    ERROR_SERVER_CSQS(2001,"请求参数缺失！"),

    ERROR_SERVER_OAUTH(4003,"权限不足！"),
    ;
    ErrorEnum(Integer state,String msg){
        this.state=state;
        this.msg=msg;
    }
    private Integer state;
    private String msg;

    public Integer getState() {
        return state;
    }

    public String getMsg() {
        return msg;
    }
}
