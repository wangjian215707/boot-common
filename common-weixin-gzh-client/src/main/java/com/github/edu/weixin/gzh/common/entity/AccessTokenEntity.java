package com.github.edu.weixin.gzh.common.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccessTokenEntity implements Serializable {

    private Integer errcode;//错误代码

    private String errmsg;//错误信息

    private String access_token;

    private Integer expires_in;

    private String refresh_token;

    private String openid;
}
