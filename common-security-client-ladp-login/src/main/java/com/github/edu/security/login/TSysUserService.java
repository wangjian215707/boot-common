package com.github.edu.security.login;


import com.github.edu.security.login.entity.TSysUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/24
 * Time: 13:53
 */
public interface TSysUserService {

    TSysUser getUser(String userId);

    boolean checkedLogin(HttpServletRequest request, HttpServletResponse response);

}
