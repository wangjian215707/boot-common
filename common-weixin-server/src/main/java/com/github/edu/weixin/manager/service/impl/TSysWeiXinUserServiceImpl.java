package com.github.edu.weixin.manager.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.weixin.manager.domain.TSysWeiXinUserDomain;
import com.github.edu.weixin.manager.entity.TSysWxUser;
import com.github.edu.weixin.manager.service.TSysWeiXinUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TSysWeiXinUserServiceImpl implements TSysWeiXinUserService {

    @Autowired
    private TSysWeiXinUserDomain domain;

    @Override
    public String getWeiXinUser(String openId) {
        JsonEntity<TSysWxUser> jsonEntity=new JsonEntity<>();
        if(StringUtils.isNotBlank(openId)){
            Optional<TSysWxUser> optional=domain.findById(openId);
            if(optional.isPresent()){
                TSysWxUser tSysWxUser=optional.get();
                jsonEntity.setData(tSysWxUser);
            }
        }
        return JsonUtils.toJson(jsonEntity);
    }

    @Override
    public String getWeixinUserByUserId(String userId) {
        JsonEntity<TSysWxUser> jsonEntity=new JsonEntity<>();
        if(StringUtils.isNotBlank(userId)){
            List<TSysWxUser> list=domain.getAllByUserid(userId);
            if(null!=list&&list.size()>0){
                jsonEntity.setData(list.get(0));
            }
        }
        return JsonUtils.toJson(jsonEntity);
    }

   /* @Override
    public TSysWxUser saveWxUser(TSysWxUser wxUser) {
        return domain.save(wxUser);
    }*/

    @Override
    public String saveWxUser(String jsonEntity) {
        JsonEntity<TSysWxUser> entity=null;
        if(StringUtils.isNotBlank(jsonEntity)){
            entity=JsonUtils.toCollection(jsonEntity, new TypeReference<JsonEntity<TSysWxUser>>() {
            });
            log.info("entity:"+entity);
            if(null!=entity){
                TSysWxUser wxUser=entity.getData();
                log.info("wxUser:"+wxUser);
                if(null!=wxUser&&StringUtils.isNotBlank(wxUser.getOpenid())){
                    wxUser=domain.save(wxUser);
                }
                entity.setData(wxUser);
            }
        }
        if(null==entity){
            entity=new JsonEntity<>();
        }
        return JsonUtils.toJson(entity);
    }

    @Override
    public String deleteWeiXinUser(String openId) {
        JsonEntity<TSysWxUser> jsonEntity=new JsonEntity<>();
        if(StringUtils.isNotBlank(openId)){
            Optional<TSysWxUser> optional=domain.findById(openId);
            if(optional.isPresent()){
                TSysWxUser tSysWxUser=optional.get();
                domain.delete(tSysWxUser);
                jsonEntity.setMsg("用户解绑成功");
            }else{
                jsonEntity.setMsg("用户未绑定");
            }
        }else{
            jsonEntity.setMsg("openId不能为空");
        }
        return JsonUtils.toJson(jsonEntity);
    }

    @Override
    public TSysWxUser findByUserId(String ck) {
        if(StringUtils.isNotBlank(ck)){
            List<TSysWxUser> list=domain.getAllByUserid(ck);
            if(null!=list&&list.size()>0){
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public TSysWxUser save(TSysWxUser wxUser) {
        return domain.save(wxUser);
    }

    @Override
    public TSysWxUser findById(String openId) {
        if(StringUtils.isNotBlank(openId)){
            Optional<TSysWxUser> optional=domain.findById(openId);
            if(optional.isPresent()){
                return optional.get();
            }
        }
        return null;
    }

    @Override
    public TSysWxUser findByCookie(String openId) {
        if(StringUtils.isNotBlank(openId)){
            Optional<TSysWxUser> optional=domain.findById(openId);
            if(optional.isPresent()){
                return optional.get();
            }
        }
        return null;
    }
}
