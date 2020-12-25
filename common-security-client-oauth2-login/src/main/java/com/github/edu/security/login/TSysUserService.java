package com.github.edu.security.login;


import com.github.edu.security.login.entity.TSysUser;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/24
 * Time: 13:53
 */
public interface TSysUserService  {

    TSysUser getUser(String userId);

}
