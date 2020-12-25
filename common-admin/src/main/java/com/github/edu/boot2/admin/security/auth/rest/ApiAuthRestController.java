package com.github.edu.boot2.admin.security.auth.rest;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.github.admin.edu.assembly.common.util.UUIDUtils;
import com.github.edu.boot2.admin.entity.TSysImageCode;
import com.github.edu.boot2.admin.security.auth.entity.*;
import com.github.edu.boot2.admin.security.auth.service.IApiAuthService;
import com.wf.captcha.ArithmeticCaptcha;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 系统接口权限授权相关接口
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/13
 */
@RestController
@RequestMapping("/rest/api/auth")
@Slf4j
public class ApiAuthRestController {


    @Autowired
    private IApiAuthService service;


    /**
     * 获取应用token
     *
     * @param appId  应用id
     * @param secret 应用key
     * @return
     */
    @GetMapping("/application/token")
    public JwtToken getToken(@RequestHeader(name = "c-appid", required = false) String appId,
                             @RequestHeader(name = "c-secret", required = false) String secret) {

        return service.getApplicationToken(appId, secret);
    }

    /**
     * 获取用户token
     *
     * @param appId  应用id
     * @param secret 应用密钥
     * @param userid 登录用户
     * @return
     */
    @GetMapping("/user/token")
    public UserToken getUserToken(@RequestHeader(name = "c-appid", required = false) String appId,
                                  @RequestHeader(name = "u-code", required = false) String userid,
                                  @RequestHeader(name = "c-secret", required = false) String secret) {

        return service.getUserTokenService(appId,secret,userid);
    }

    /**
     * 用户登录
     * @param authUser
     * @param request
     * @return
     */
    @PostMapping(value = "/login")
    public Map<String, Object> login(@Validated @RequestBody UserLogin authUser, HttpServletRequest request){

        return service.userAuthLogin(authUser, request);
    }



    /**
     * 验证码功能
     * @return
     */
    @GetMapping("/image/code")
    public ImageCode getImageCode(){

        return service.getUserClientCode();
    }

}
