package com.github.edu.boot2.admin.rest.controller;

import com.github.edu.boot2.admin.entity.TSysRole;
import com.github.edu.boot2.admin.entity.TSysUser;
import com.github.edu.boot2.admin.rest.exception.entity.Result;
import com.github.edu.boot2.admin.service.ISysUserManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户基本信息
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
@RestController
@RequestMapping("/rest/api/security/info")
public class UserInformationRestController {

    @Autowired
    private ISysUserManagerService service;

    /**
     * 获取用户基本信息
     * @param userId 用户登陆账号
     * @return 用户基本信息
     */
    @GetMapping("/userInformation/get")
    public TSysUser getUserInformation(@RequestParam("userId") String userId){
        return service.getUserInformation(userId);
    }

    /**
     * 
     * @param userId
     * @return
     */
    @GetMapping("/user/role")
    public TSysRole getUserRole(@RequestParam("userId")String userId){

        return null;
    }

    /**
     * 保存用户基本信息
     * @return
     */
    @PostMapping("/userInformation/save")
    public TSysUser saveOrUpdate(@RequestBody TSysUser tSysUser){
        return service.saveOrUpdate(tSysUser);
    }

    /**
     * 删除
     * @param uid
     * @return
     */
    @GetMapping("/userInformation/del")
    public Object del(@RequestParam("uid")String uid){
        String val=service.delete(uid);
        return new Result(val);
    }




}
