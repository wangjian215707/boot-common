package com.github.edu.security.login.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.GetCasTGTService;
import com.github.edu.security.login.IUserWxUserService;
import com.github.edu.security.login.UserService;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.SecurityUser;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.util.CookieManagerUtil;
import com.github.edu.security.login.util.DigestUtil;
import com.github.edu.weixin.gzh.common.entity.TSysWxUser;
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

    @Value("${weixin.custom.cas-domain}")

    private String casDomain;

    @Autowired
    private UserLoginInterfaceDomain domain;

    @Value("${weixin.custom.domain}")
    private String domainstr;

    @Autowired
    private GetCasTGTService tgtService;

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
    public boolean openIdLogin(HttpServletRequest request, HttpServletResponse response, String openId) {
        boolean info = false;
        log.info("---------------开始执行openid登陆！");
        if (StringUtils.isNotBlank(openId)) {
            TSysWxUser wxUser = wxService.findById(openId);
            if (null != wxUser) {
                if (StringUtils.isNotBlank(wxUser.getPwd()) && StringUtils.isNotBlank(wxUser.getUserid())) {
                    TSysUser tSysUser = getUser(wxUser.getUserid());
                    if (null != tSysUser) {
                        String password = wxUser.getPwd();
                        if (StringUtils.isNotBlank(password)) {
                            try {
                                String tgt = tgtService.getTicketGrantingTicket(wxUser.getUserid(), DigestUtil.md5(password));
                                log.info("-------------openid tgt" + tgt);
                                if (StringUtils.isNotBlank(tgt)&&tgt.indexOf("exceptions")==-1) {
                                    request.getSession().setAttribute("SESSION_USER_BEAN", tSysUser);
                                    UserDetails userDetails = new SecurityUser(tSysUser);
                                    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
                                    SecurityContextHolder.getContext().setAuthentication(authentication);
                                    info = true;
                                }
                            } catch (Exception e) {
                                throw new BadCredentialsException("用户认证失败");
                            }
                        }
                    }
                }
            }
        }
        return info;
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
    private IUserWxUserService wxService;

    @Override
    public boolean checkedLogin(HttpServletRequest request, HttpServletResponse response) {
        String ck = getDecodedCookieValue(request, "iPlanetDirectoryPro");
        if (StringUtils.isNotBlank(ck)) {
            TSysWxUser tSysWxUser = wxService.findByCookie(ck);
            if (null != tSysWxUser) {
                String userId = tSysWxUser.getUserid();
                if (StringUtils.isNotBlank(userId)) {
                    TSysUser sysUser = getUser(userId);
                    String tgt = getDecodedCookieValue(request, casDomain);
                    if (StringUtils.isBlank(tgt)) {
                        tgt = tgtService.getTicketGrantingTicket( tSysWxUser.getUserid(),tSysWxUser.getPwd(),request);
                    }
                    if (StringUtils.isBlank(tgt)) {
                        return false;
                    }
                    CookieManagerUtil.addCookie(response, tgt, domainstr);
                    if (null != sysUser) {
                        UserDetails userDetails = new SecurityUser(sysUser);
                        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "", userDetails.getAuthorities());
                        SecurityContextHolder.getContext().setAuthentication(authentication);
                        return true;

                    }
                }
            }
        }
        return false;
    }

}
