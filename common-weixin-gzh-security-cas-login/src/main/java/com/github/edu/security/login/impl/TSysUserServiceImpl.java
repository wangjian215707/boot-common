package com.github.edu.security.login.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.TSysUserService;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.SecurityUser;
import com.github.edu.security.login.entity.TSysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/24
 * Time: 14:04
 */
@Slf4j
public class TSysUserServiceImpl implements TSysUserService, UserDetailsService {

    @Autowired
    private UserLoginInterfaceDomain domain;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TSysUser user = getUser(s);
        if (user == null) {
            log.error("user login error : userId not find ! 没有发现用户登录账号！");
            throw new UsernameNotFoundException("UserName " + s + " not found");
        }
        SecurityUser securityUser = new SecurityUser(user);
        return securityUser;
    }

    private TSysUser getUser(String userId){
        log.info("---------系统登录用户："+userId);
        if(StringUtils.isNotBlank(userId)){
            String json=domain.getUserLoginInformation(userId);
            if(StringUtils.isNotBlank(json)){
                JsonEntity<TSysUser> jsonEntity= JsonUtils.toCollection(json, new TypeReference<JsonEntity<TSysUser>>() {
                });
                if(null!=jsonEntity){
                    return jsonEntity.getData();
                }
            }
        }
        return null;
    }
}
