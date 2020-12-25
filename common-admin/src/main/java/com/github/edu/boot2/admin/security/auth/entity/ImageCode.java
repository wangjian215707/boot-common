package com.github.edu.boot2.admin.security.auth.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
@Data
public class ImageCode extends ErrorMessage implements Serializable {

    public String img;

    public String uuid;

}
