package com.github.edu.security.login;


import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.weixin.gzh.common.entity.TSysWxUser;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-28
 */
public interface IUserWxUserService {

    Boolean checkedUser(String userId);

    Boolean getAndSaveUserOpenId(String code,String userId);

    TSysWxUser save(TSysWxUser wxUser);

    TSysWxUser findById(String openId);

    TSysWxUser findByCookie(String ck);

    TSysUser getUser(String userId);
}
