package com.github.admin.edu.assembly.exception.entity;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-20
 */
public class BaseResponse {

    private int status = 200;
    private String message;

    public BaseResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public BaseResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
