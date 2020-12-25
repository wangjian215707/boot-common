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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        String code = domain.saveWxUser(json);
        jsonEntity = JsonUtils.toCollection(code, new TypeReference<JsonEntity<TSysWxUser>>() {
        });
        TSysWxUser entity = jsonEntity.getData();
        return entity;
    }

    @Override
    public TSysWxUser findById(String openId) {
        if(StringUtils.isNotBlank(openId)){
            String code=domain.getWeiXinUser(openId);
            if(StringUtils.isNotBlank(code)){
                JsonEntity<TSysWxUser> jsonEntity= JsonUtils.toCollection(code, new TypeReference<JsonEntity<TSysWxUser>>() {
                });
                if(null!=jsonEntity){
                    return jsonEntity.getData();
                }
            }
        }
        return null;
    }
}
