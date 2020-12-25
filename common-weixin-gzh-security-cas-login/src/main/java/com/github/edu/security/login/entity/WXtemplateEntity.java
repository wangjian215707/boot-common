package com.github.edu.security.login.entity;

import lombok.Data;

import java.util.Map;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-28
 */
@Data
public class WXtemplateEntity {

    private String touser;//openid

    private String template_id;//模版编号

    private String url;

    private Map<String,Object> miniprogram;

    private Map<String,Object> data;
}
