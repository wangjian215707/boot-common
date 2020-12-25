package com.github.edu.security.login.impl;

import com.github.admin.edu.assembly.http.util.HttpUtil;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.IWxSendMessage;
import com.github.edu.weixin.gzh.common.service.AccessTokenManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-28
 */
@Slf4j
@Service
public class WxSendMessageServiceImpl implements IWxSendMessage {

    @Autowired
    private AccessTokenManagerService service;

    @Autowired
    private HttpUtil httpUtil;

    @Override
    public String sendMessage(String code) {
        String url="https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=";
        if(StringUtils.isNotBlank(code)){
            String access_token=service.getAccessToken();
            url=url+access_token;
            if(StringUtils.isNotBlank(code)){
               String msg= httpUtil.responseJsonServicePostString(url,code);
               log.info("---消息推送："+msg);
            }
        }
        return null;
    }
}
