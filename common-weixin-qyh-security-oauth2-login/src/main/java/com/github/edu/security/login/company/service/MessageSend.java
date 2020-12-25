package com.github.edu.security.login.company.service;
import com.github.admin.edu.assembly.http.util.HttpUtil;
import com.github.edu.security.login.company.entity.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 18424 on 2018/1/21.
 *
 */
@Component
public class MessageSend implements Serializable {
    private static final Logger LOGGER =  LoggerFactory.getLogger(MessageSend.class);

    @Autowired
    private HttpUtil httpUtil;
    @Autowired
    private AccessTokenService accessTokenService;

    public  void sendMessage(String userid,String content,String ajid,String corpsecret,String corpid){
        LOGGER.info("begin send notice------“"+userid+"”------------------:"+content);
        Map<String,Object> map=new HashMap<>();
        map.put("touser",userid);
        map.put("toparty","");
        map.put("totag","");
        map.put("msgtype","text");
        map.put("agentid",Integer.parseInt(ajid));
        Text contents=new Text();
        contents.setContent(content);
        map.put("text",contents);
        map.put("safe",0);
        String msg= httpUtil.responseJsonServicePost
                ("https://qyapi.weixin.qq.com/cgi-bin/message/send?access_token="+accessTokenService.getAccessToken(corpsecret,corpid),map,null);
        LOGGER.info(msg);
    }
}
