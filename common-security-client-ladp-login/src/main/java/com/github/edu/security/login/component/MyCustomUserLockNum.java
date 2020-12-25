package com.github.edu.security.login.component;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户锁定次数
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
 */
@Component
public class MyCustomUserLockNum {


    public int getUserLockMap(HttpServletRequest request, String userId){
        int num=0;
        if(null!=request.getSession().getAttribute(userId+"_lock_user")){
            num= (int)request.getSession().getAttribute(userId+"_lock_user");
        }
        return num;
    }
    public void setUserLockNum(HttpServletRequest request, String userId,int num){
        request.getSession().setAttribute(userId+"_lock_user",num);
    }
    public void removeLockNum(HttpServletRequest request, String userId){
        request.getSession().removeAttribute(userId+"_lock_user");
    }

}
