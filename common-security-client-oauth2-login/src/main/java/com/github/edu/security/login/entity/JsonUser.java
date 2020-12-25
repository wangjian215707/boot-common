package com.github.edu.security.login.entity;


import lombok.Data;

@Data
public class JsonUser {

    private String access_token;

    private String refresh_token;

    private String scope;

    private String id_token;

    private String token_type;

    private String expires_in;
}
