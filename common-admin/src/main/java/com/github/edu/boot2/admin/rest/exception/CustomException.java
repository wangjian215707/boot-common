package com.github.edu.boot2.admin.rest.exception;

import lombok.Data;

/**
 * 自定义异常处理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
@Data
public class CustomException extends RuntimeException {

    private Integer code;

    private String message;

    public CustomException(Integer code,String message){
        this.code=code;
        this.message=message;
    }
    public CustomException(){
        this.code=0;
        this.message="OK";
    }
}
