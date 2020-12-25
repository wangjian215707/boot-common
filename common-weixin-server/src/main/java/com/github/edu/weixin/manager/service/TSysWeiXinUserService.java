package com.github.edu.weixin.manager.service;

import com.github.edu.weixin.manager.entity.TSysWxUser;

public interface TSysWeiXinUserService {

    String getWeiXinUser(String openId);

    String getWeixinUserByUserId(String userId);

    String saveWxUser(String jsonEntity);

    String deleteWeiXinUser(String openId);

    TSysWxUser findByUserId(String userid);

    TSysWxUser save(TSysWxUser wxUser);

    TSysWxUser findById(String openId);

    TSysWxUser findByCookie(String cookie);
}
