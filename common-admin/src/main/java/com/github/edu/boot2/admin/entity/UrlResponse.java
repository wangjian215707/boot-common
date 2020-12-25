package com.github.edu.boot2.admin.entity;

import lombok.Data;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/22
 */
@Data
public class UrlResponse {

    private boolean success;

    private Integer code;

    private String message;

    private String date;
}
