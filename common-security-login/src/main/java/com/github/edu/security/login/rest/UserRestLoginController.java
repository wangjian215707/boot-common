package com.github.edu.security.login.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/24
 * Time: 14:23
 */
@RestController
@RequestMapping("/rest/api/user/login")
@Api(tags = "2.0",description = "用户登录管理",value = "用户登录管理")
@Slf4j
public class UserRestLoginController {

    @ApiOperation(value = "登录账号查询（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId",value = "登录账号",dataType = "String",paramType = "query",required = true)
    })

    @GetMapping("/entity")
    public String getUserEntity(@RequestParam("userId")String userId){

        return "";
    }

    /**
     * 查询用户登陆失败次数
     * @param userId
     * @return
     */
    @GetMapping("/logs/error")
    public String queryUserLoginError(@RequestParam("userId")String userId){

        return "";
    }



}

