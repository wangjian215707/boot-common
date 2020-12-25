package com.github.edu.security.login.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.SecurityUser;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.entity.TSysWxUser;
import com.github.edu.security.login.service.UserService;
import com.github.edu.security.login.service.UserWxService;
import com.github.edu.security.login.util.CookieManagerUtil;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import com.wiscom.is.SSOToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static com.github.edu.security.login.util.CookieManagerUtil.getDecodedCookieValue;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-4-16
 */
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    @Value("${server.custom.login.enabled}")
    private boolean enabled;

    @Autowired
    private UserLoginInterfaceDomain domain;

    @Value("${server.custom.ice.proxy}")
    private String proxy;
    @Value("${server.custom.ice.username}")
    private String userName;
    @Value("${server.custom.ice.password}")
    private String pwd;
    @Value("${server.custom.ice.domain}")
    private String iceDomain;
    @Value("${server.custom.ice.file}")
    private String file;
    @Value("${server.custom.ice.file1}")
    private String file1;

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


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        TSysUser user = getUser(username);
        if (user == null) {
            throw new UsernameNotFoundException("UserName " + username + " not found");
        }
        SecurityUser securityUser = new SecurityUser(user);
        return securityUser;
    }

    @Autowired
    private UserWxService wxService;


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

    @Override
    public boolean openIdLogin(HttpServletRequest request, HttpServletResponse response,String openId) {
        boolean info=false;
        log.info("---------------开始执行openid登陆！");
        if(StringUtils.isNotBlank(openId)){
            TSysWxUser wxUser=wxService.findById(openId);
            if(null!=wxUser){
                if(StringUtils.isNotBlank(wxUser.getPwd())&&StringUtils.isNotBlank(wxUser.getUserid())){
                    TSysUser tSysUser = getUser(wxUser.getUserid());
                    if(null!=tSysUser){
                        String password=wxUser.getPwd();
                        IdentityFactory factory = null;
                        try {
                            factory = IdentityFactory.createFactory(file);
                            IdentityManager manager = factory.getIdentityManager();
                            info = manager.checkPassword(tSysUser.getUserid(), password);
                            if(info){
                                IdentityManager im = factory.getIdentityManager();
                                SSOToken token = im.createStoken(tSysUser.getUserid(), password);
                                CookieManagerUtil.addCookie(response, token.getTokenValue(), iceDomain);
                                request.getSession().setAttribute("SESSION_USER_BEAN", tSysUser);
                                UserDetails userDetails = new SecurityUser(tSysUser);
                                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
                                SecurityContextHolder.getContext().setAuthentication(authentication);
                            }
                        } catch (Exception e) {
                            throw new BadCredentialsException("链接ldap服务器异常");
                        }
                    }
                }
            }
        }
        return info;
    }

}
