package com.github.admin.edu.assembly.exception.auth;

import com.github.admin.edu.assembly.exception.BaseException;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-19
 */
public class ClientTokenException extends BaseException {

    public ClientTokenException(String message) {
        super(message, 3001);
    }

    public ClientTokenException(String message,Integer code){
        super(message,code);
    }
}
