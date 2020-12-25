package com.github.edu.boot2.admin.security.compnent;

import com.github.edu.boot2.admin.entity.TSysImageCode;
import com.github.edu.boot2.admin.entity.TSysOnlineUser;
import com.github.edu.boot2.admin.entity.TSysUserToken;
import com.github.edu.boot2.admin.service.ISysImageCodeService;
import com.github.edu.boot2.admin.service.ISysUserOnlineService;
import com.github.edu.boot2.admin.service.ISysUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.Future;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/21
 */
@Component
@Async
public class UserTokenComponent {

    @Autowired
    private ISysUserTokenService service;

    public Future<Object> saveUserToken(TSysUserToken tSysUserToken){
        tSysUserToken=service.saveOrUpdate(tSysUserToken);
        return new AsyncResult<>(tSysUserToken);
    }

    @Autowired
    private ISysImageCodeService imageCodeService;

    public Future<Object> saveImageCode(TSysImageCode tSysImageCode){

        tSysImageCode=imageCodeService.saveOrUpdate(tSysImageCode);
        return new AsyncResult<>(tSysImageCode);
    }

    public Future<Object> delete(String ids){

        imageCodeService.delete(ids);
        return new AsyncResult<>("ok");
    }

    @Autowired
    private ISysUserOnlineService onlineService;

    public Future<Object> deleteOnlineUserExceptToken(String userId,String token){
        int i=onlineService.deleteOnlineUser(userId,token);
        return new AsyncResult<>(i);
    }

    public Future<Object> saveOnlineUser(TSysOnlineUser tSysOnlineUser){

        tSysOnlineUser=onlineService.saveOrUpdate(tSysOnlineUser);
        return new AsyncResult<>(tSysOnlineUser);
    }
}
