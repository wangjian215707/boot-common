package com.github.edu.weixin.gzh.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-12-12
 */
@Data
public class TSysWxUser implements Serializable {

    //private WxUserInformation wxUserInformation;

    private String openid;

    private String image;

    private String userid;

    private String pwd;

    private String cookie;

    private String name;

    public TSysWxUser(){

    }

   /* public TSysWxUser(WxUserInformation userInformation){
        this.wxUserInformation=userInformation;
    }*/


    public String getOpenid() {
      /*  if(null!=this.wxUserInformation){
            return this.wxUserInformation.getOpenid();
        }*/
        return openid;
    }

    public String getImage() {
       /* if(null!=this.wxUserInformation){
            return this.wxUserInformation.getHeadimgurl();
        }*/
        return image;
    }

    public String getUserid() {
        return userid;
    }

    public String getPwd() {
        return pwd;
    }

    public String getCookie() {
        return cookie;
    }

    public String getName() {
      /*  if(null!=this.wxUserInformation){
            return this.wxUserInformation.getNickname();
        }*/
        return name;
    }
}
