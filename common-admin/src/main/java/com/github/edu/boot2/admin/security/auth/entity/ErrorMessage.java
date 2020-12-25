package com.github.edu.boot2.admin.security.auth.entity;

import lombok.Data;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/14
 */
@Data
public abstract class ErrorMessage {

    private String errorMessage;

    private Integer errorCode;
}
