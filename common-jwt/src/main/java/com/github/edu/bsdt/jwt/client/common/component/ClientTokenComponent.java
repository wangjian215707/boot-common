package com.github.edu.bsdt.jwt.client.common.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.http.util.HttpUtil;
import com.github.admin.edu.assembly.jwt.entity.JwtTokenEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/16
 * Time: 0:11
 */
@Component
@Slf4j
public class ClientTokenComponent {

    @Autowired
    private HttpUtil httpUtil;

    @Value("${server.custom.token.jwt.client-key}")
    private String clientKey;

    @Value("${server.custom.token.jwt.client-id}")
    private String clientId;

    @Value("${server.custom.token.jwt.server-path}")
    private String path;

    @Cacheable(value = "jwt_token")
    public String getToken(){
        String code=httpUtil.responseServiceGet(path+"/rest/api/edu/jwt/get/token?clientId="+clientId+"&pubKey="+clientKey);
        if(!StringUtils.isEmpty(code)){
            JwtTokenEntity jwtTokenEntity= JsonUtils.toCollection(code, new TypeReference<JwtTokenEntity>() {
            });
            if(null!=jwtTokenEntity&&200==jwtTokenEntity.getState()){
                return jwtTokenEntity.getToken();
            }
            if(null!=jwtTokenEntity){
                log.info("--------------:"+jwtTokenEntity.getMsg());
            }else {
                log.info("--------------:未获取到token");
            }
        }
        return "";
    }

}
