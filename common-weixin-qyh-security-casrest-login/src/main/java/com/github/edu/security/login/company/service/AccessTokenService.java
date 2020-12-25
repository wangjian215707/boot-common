package com.github.edu.security.login.company.service;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.http.util.HttpUtil;
import com.github.edu.security.login.company.entity.AccessTokenEntity;
import com.github.edu.security.login.company.entity.UserTicket;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by Intellij IDE
 * User wangjian
 * Date 2017/10/26
 * Time 16:57
 * Version V1.01
 */
@Service
@Slf4j
public class AccessTokenService {

    private static final long serialVersionUID = -8685285401859800066L;

    private static final Logger LOGGER= LoggerFactory.getLogger(AccessTokenService.class);

    @Autowired
    private HttpUtil httpUtil;


    @Value("${weixin.custom.corpid}")
    private String corpid;

    private static final String url_token="https://qyapi.weixin.qq.com/cgi-bin/gettoken";

    @Cacheable(value = "access_token",key ="#corpSecret")
    public String getAccessToken(String corpSecret){
        String code=httpUtil.responseServiceGet(url_token+"?corpid="+corpid+"&corpsecret="+corpSecret);
        if(!StringUtils.isEmpty(code)){
            AccessTokenEntity any= JsonUtils.toCollection(code, new TypeReference<AccessTokenEntity>() {});
            if (null != any) {
                return any.getAccess_token();
            } else {
                LOGGER.error("获取accesstoken错误：" + any.getErrmsg());
            }
        }
        return "";
    }

    /**
     * 用户accesstoken超时，出现token验证失败时，缓存中保存的是错误的token
     * 重新刷新缓存token
     * @return
     */
    @CacheEvict(value = "access_token",key = "#corpSecret")
    public String getNewAccessToken(String corpSecret){
        String code=httpUtil.responseServiceGet(url_token+"?corpid="+corpid+"&corpsecret="+corpSecret);
        if(!StringUtils.isEmpty(code)){
            AccessTokenEntity any= JsonUtils.toCollection(code, new TypeReference<AccessTokenEntity>() {});
            if (null != any) {
                return any.getAccess_token();
            } else {
                LOGGER.error("获取accesstoken错误：" + any.getErrmsg());
            }
        }
        return "";
    }

    /**
     * 获取用户信息需要的ticket token
     * @param accesstoken
     * @param code
     * @return
     */
    @Cacheable(value = "ticket_token",key = "#code")
    public String getUserTicket(String accesstoken,String code){
        String info=httpUtil.responseServiceGet("https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo?access_token="+accesstoken+"&code="+code);
        if(!StringUtils.isEmpty(info)){
            UserTicket any= JsonUtils.toCollection(info, new TypeReference<UserTicket>() {
            });
            log.info("any"+any);
            if (null != any) {
                return any.getUser_ticket();
            } else {
                LOGGER.error("获取accesstoken错误：" + any.getErrmsg());
            }
        }
        return "";
    }

}
