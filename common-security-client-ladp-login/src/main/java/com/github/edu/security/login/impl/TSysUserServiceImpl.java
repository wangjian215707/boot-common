package com.github.edu.security.login.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.TSysUserService;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.SecurityUser;
import com.github.edu.security.login.entity.TSysUser;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.github.edu.security.login.util.ICECookieUtil.getDecodedCookieValue;

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

    public TSysUser getUser(String userId){
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

    @Value("${server.custom.ice.file}")
    private String file;
    @Value("${server.custom.ice.file1}")
    private String file1;

    @Override
    public boolean checkedLogin(HttpServletRequest request, HttpServletResponse response) {
        String ck = getDecodedCookieValue(request, "iPlanetDirectoryPro");
        log.info("检测cookie:" + ck);
        if (StringUtils.isNotBlank(ck)) {
            IdentityFactory factory = null;
            try {
                factory = IdentityFactory.createFactory(file);
                IdentityManager im = factory.getIdentityManager();
                String userid = im.getCurrentUser(ck);
                if (StringUtils.isBlank(userid)) {
                    return false;
                }
                TSysUser tSysUser = getUser(userid);
                if (null != tSysUser) {
                    request.getSession().setAttribute("SESSION_USER_BEAN", tSysUser);
                    UserDetails userDetails = new SecurityUser(tSysUser);
                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
