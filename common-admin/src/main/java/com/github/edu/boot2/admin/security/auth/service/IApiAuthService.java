package com.github.edu.boot2.admin.security.auth.service;

import com.github.edu.boot2.admin.security.auth.entity.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 系统授权，获取token
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/14
 */
public interface IApiAuthService {

    /**
     * 获取应用接口授权
     * @param appId
     * @param secret
     * @return
     */
    JwtToken getApplicationToken(String appId,String secret);

    /**
     * 获取用户token
     * @param appId
     * @param secret
     * @param code
     * @return
     */
    UserToken getUserTokenService(String appId,String secret,String code);

    /**
     * 获取验证码信息
     * @return
     */
    ImageCode getUserClientCode();

    /**
     * 用户登录
     * @param userLogin
     * @param request
     * @return
     */
    Map<String,Object> userAuthLogin(UserLogin userLogin, HttpServletRequest request);
}
