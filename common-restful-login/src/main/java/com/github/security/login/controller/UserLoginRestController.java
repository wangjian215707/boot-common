package com.github.security.login.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登陆相关接口
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
@RestController
@RequestMapping("/rest/api/security/login")
public class UserLoginRestController {

    /**
     * 获取用户基本信息
     * @param userId 用户登陆账号
     * @return 用户基本信息
     */
    @GetMapping("/userInformation")
    public String getUserInformation(String userId){

        return "";
    }



}
