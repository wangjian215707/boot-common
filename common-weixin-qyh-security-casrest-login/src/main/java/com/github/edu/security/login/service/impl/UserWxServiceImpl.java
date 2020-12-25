package com.github.edu.security.login.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.TSysWxUser;
import com.github.edu.security.login.service.UserWxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/3
 * Time: 21:02
 */
@Service
@Slf4j
public class UserWxServiceImpl implements UserWxService {

    @Autowired
    private UserLoginInterfaceDomain domain;


    @Override
    public TSysWxUser findByCookie(String ck) {
        if(StringUtils.isNotBlank(ck)){
            String code=domain.getWxUserByCookieString(ck);
            if(StringUtils.isNotBlank(code)){
                JsonEntity<TSysWxUser> jsonEntity=JsonUtils.toCollection(code, new TypeReference<JsonEntity<TSysWxUser>>() {
                });
                if(null!=jsonEntity){
                    return jsonEntity.getData();
                }
            }
        }
        return null;
    }

    @Override
    public TSysWxUser save(TSysWxUser wxUser) {
        JsonEntity<TSysWxUser> jsonEntity=new JsonEntity<>();
        jsonEntity.setData(wxUser);
        String json = JsonUtils.toJson(jsonEntity);
        log.info("---他提交服务器保存的用户信息--："+json);
        String code = domain.saveWxUser(json);
        log.info("==保存信息："+code);
        jsonEntity = JsonUtils.toCollection(code, new TypeReference<JsonEntity<TSysWxUser>>() {
        });
        TSysWxUser entity = jsonEntity.getData();
        return entity;
    }

    @Override
    public TSysWxUser findById(String openId) {
        log.info("---根据openid 查询用户");
        if(StringUtils.isNotBlank(openId)){
            String code=domain.getWeiXinUser(openId);
            log.info("-------微信用户code："+code);
            if(StringUtils.isNotBlank(code)){
                JsonEntity<TSysWxUser> jsonEntity= JsonUtils.toCollection(code, new TypeReference<JsonEntity<TSysWxUser>>() {
                });
                log.info("---------json 转换");
                if(null!=jsonEntity){
                    log.info("------------取到值："+jsonEntity.getData());
                    return jsonEntity.getData();
                }
            }
        }
        return null;
    }
}
