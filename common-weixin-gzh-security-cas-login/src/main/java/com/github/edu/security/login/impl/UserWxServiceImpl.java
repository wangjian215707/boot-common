package com.github.edu.security.login.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.IUserWxUserService;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.weixin.gzh.common.entity.AccessTokenEntity;
import com.github.edu.weixin.gzh.common.entity.TSysWxUser;
import com.github.edu.weixin.gzh.common.service.AccessTokenWebService;
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
public class UserWxServiceImpl implements IUserWxUserService {

    @Autowired
    private UserLoginInterfaceDomain domain;

    @Autowired
    private AccessTokenWebService webService;

    @Override
    public Boolean checkedUser(String userId){
        if(StringUtils.isNotBlank(userId)){
            String code=domain.getWeiXinUserByUserId(userId);
            if(StringUtils.isNotBlank(code)){
                JsonEntity<TSysWxUser> jsonEntity= JsonUtils.toCollection(code, new TypeReference<JsonEntity<TSysWxUser>>() {
                });
                if(null!=jsonEntity){
                    TSysWxUser tSysWxUser=jsonEntity.getData();
                    if(null!=tSysWxUser&&StringUtils.isNotBlank(tSysWxUser.getOpenid())){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public Boolean getAndSaveUserOpenId(String code, String userId) {
        if(StringUtils.isNotBlank(code)&&StringUtils.isNotBlank(userId)){
            AccessTokenEntity accessTokenEntity=webService.getAccessToken(code);
            if(null!=accessTokenEntity&&StringUtils.isNotBlank(accessTokenEntity.getOpenid())){
                String openId=accessTokenEntity.getOpenid();
                TSysWxUser tSysWxUser=new TSysWxUser();
                tSysWxUser.setOpenid(openId);
                tSysWxUser.setUserid(userId);
                JsonEntity<TSysWxUser> jsonEntity=new JsonEntity<>();
                jsonEntity.setData(tSysWxUser);
                String info =domain.saveWxUser(JsonUtils.toJson(jsonEntity));
                log.info("--info:"+info);
                return true;
            }
        }
        return false;
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
    public TSysUser getUser(String userId) {
        log.info("---------系统登录用户：" + userId);
        if (StringUtils.isNotBlank(userId)) {
            String json = domain.getUserLoginInformation(userId);
            if (StringUtils.isNotBlank(json)) {
                JsonEntity<TSysUser> jsonEntity = JsonUtils.toCollection(json, new TypeReference<JsonEntity<TSysUser>>() {
                });
                if (null != jsonEntity) {
                    return jsonEntity.getData();
                }
            }
        }
        return null;
    }
}
