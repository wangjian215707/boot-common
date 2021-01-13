package com.github.edu.security.login.company.service;


/**
 * Created by 18424 on 2017/11/8.
 */

/**
 * 主动推送消息接口
 */
public interface SMessageApi {

    String STextMessage(String accessToken, MessageSend messageSend);
}
