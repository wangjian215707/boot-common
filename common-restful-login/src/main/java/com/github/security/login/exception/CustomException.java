package com.github.security.login.exception;

import lombok.Getter;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
@Getter
public class CustomException extends RuntimeException {

    private int code;
    private String message;

    public CustomException(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
