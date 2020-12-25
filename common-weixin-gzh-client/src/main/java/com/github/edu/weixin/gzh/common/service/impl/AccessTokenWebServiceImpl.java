package com.github.edu.weixin.gzh.common.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.http.util.HttpUtil;
import com.github.edu.weixin.gzh.common.entity.AccessTokenEntity;
import com.github.edu.weixin.gzh.common.service.AccessTokenWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AccessTokenWebServiceImpl implements AccessTokenWebService {

    @Value("${weixin.custom.appid}")
    private String appId;

    @Value("${weixin.custom.secret}")
    private String secret;

    @Autowired
    private HttpUtil httpUtil;

    private static final String token_url = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
    @Cacheable(value = "manager_access_token",key = "#cod")
    public AccessTokenEntity getAccessToken(String cod) {
        String code = httpUtil.responseServiceGet(token_url + "&appid=" + appId + "&secret=" + secret + "&code=" + cod);
        if (!StringUtils.isEmpty(code)) {
            AccessTokenEntity any = JsonUtils.toCollection(code, new TypeReference<AccessTokenEntity>() {
            });
            if (null != any) {
                if(null!=any.getErrcode()){
                    log.error("获取accesstoken错误：" + any.getErrmsg());
                    return null;
                }
                return any;

            }
        }
        return null;
    }
}
