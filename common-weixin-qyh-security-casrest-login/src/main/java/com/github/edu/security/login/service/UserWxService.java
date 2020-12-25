package com.github.edu.security.login.service;

import com.github.edu.security.login.entity.TSysWxUser;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/3
 * Time: 21:01
 */
public interface UserWxService {

    TSysWxUser findByCookie(String ck);

    TSysWxUser save(TSysWxUser wxUser);

    TSysWxUser findById(String openId);
}
