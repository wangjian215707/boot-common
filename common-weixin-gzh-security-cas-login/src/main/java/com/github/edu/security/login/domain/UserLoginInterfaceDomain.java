package com.github.edu.security.login.domain;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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

    /**
     * 根据openid获取用户基本新
     * @param openId
     * @return
     */
    @GetMapping("/rest/api/weixin/user/info")
    String getWeiXinUser(@RequestParam("openId")String openId);

    /**
     * 根据userid获取用户基本信息
     * @param userId
     * @return
     */
    @GetMapping("/rest/api/weixin/user/uid")
    String getWeiXinUserByUserId(@RequestParam("userId")String userId);

    @GetMapping("/rest/api/weixin/user/cookie/info")
    String getWxUserByCookieString(@RequestParam("cookie")String cookie);
    /**
     * 保存用户基本信息
     * @param jsonEntity
     * @return
     */
    @PostMapping("/rest/api/weixin/user/save")
    String saveWxUser(@RequestBody String jsonEntity);



}
