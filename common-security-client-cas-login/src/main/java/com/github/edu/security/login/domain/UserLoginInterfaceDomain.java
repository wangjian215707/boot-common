package com.github.edu.security.login.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-11-27
 */
public interface UserLoginInterfaceDomain {

    /**
     * 根据用户账号，获取登录用户基本信息
     * @param userId 用户登录账号
     * @return Json JsonEntity
     */
    @GetMapping("/rest/api/user/auth/login")
    String getUserLoginInformation(@RequestParam("userId") String userId);
}
