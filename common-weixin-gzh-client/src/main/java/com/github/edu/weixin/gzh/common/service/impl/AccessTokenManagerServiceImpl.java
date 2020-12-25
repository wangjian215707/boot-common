package com.github.edu.weixin.gzh.common.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.http.util.HttpUtil;
import com.github.edu.weixin.gzh.common.entity.AccessTokenEntity;
import com.github.edu.weixin.gzh.common.service.AccessTokenManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AccessTokenManagerServiceImpl implements AccessTokenManagerService {

    @Value("${weixin.custom.appid}")
    private String appId;

    @Value("${weixin.custom.secret}")
    private String secret;

    @Autowired
    private HttpUtil httpUtil;

    private static final String url_token ="https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential";


    @Cacheable(value = "manager_access_token")
    @Override
    public String getAccessToken() {
        String code = httpUtil.responseServiceGet(url_token + "&appid=" + appId + "&secret=" + secret);
        if (!StringUtils.isEmpty(code)) {
            AccessTokenEntity any = JsonUtils.toCollection(code, new TypeReference<AccessTokenEntity>() {
            });
            if (null != any) {
                return any.getAccess_token();
            } else {
                log.error("获取accesstoken错误：" + any.getErrmsg());
            }
        }
        return null;
    }
}
