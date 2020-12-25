package com.github.edu.client.common.util;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/3/2
 */
public enum SortEnum {

    DESC(0,"desc"),
    ASC(1,"ASC")
    ;

    SortEnum(int code,String name){
        this.code=code;
        this.name=name;
    }

    private int code;

    private String name;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
