package com.github.edu.security.login.configuration;

import com.github.edu.security.login.entity.CasTgc;
import com.github.edu.security.login.entity.Openid;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.entity.TSysWxUser;
import com.github.edu.security.login.service.GetCasTGTService;
import com.github.edu.security.login.service.UserService;
import com.github.edu.security.login.service.UserWxService;
import com.github.edu.security.login.util.DigestUtil;
import com.github.edu.security.login.util.TGTDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * 自定义用户认证
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/4
 * Time: 11:51
 */
@Slf4j
@Component
public class CustomUserAuthenticationManager implements AuthenticationProvider {

    @Autowired
    private UserService service;

    @Autowired
    private UserWxService wxService;

    @Autowired
    private GetCasTGTService tgtService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String username=authentication.getName();
        log.info("认证username："+username);
        if(StringUtils.isEmpty(username)){
            throw new BadCredentialsException("用户名登录账号不能为空！");
        }
        String password= (String) authentication.getCredentials();
        log.info("认证password："+password);
        String openid="";
        List<Openid> list= (List<Openid>) authentication.getAuthorities();
        if (null!=list&&list.size()>0){
            openid=list.get(0).getAuthority();
            log.info("认证openid："+openid);
        }
        if(StringUtils.isEmpty(password)){
            throw new BadCredentialsException("登录密码不能为空！");
        }
        if(StringUtils.isEmpty(openid)){
            throw new BadCredentialsException("未获取到用户openid！");
        }
        TSysUser user =service.getUser(username);
        log.info("认证用户信息"+user);
        if(null!=user){
            String tgt= tgtService.getTicketGrantingTicket(username,password);
            log.info("tgt:"+tgt);
            if(!StringUtils.isEmpty(tgt)){
                TSysWxUser wxUser=wxService.findById(openid);
                log.info("---------取到用户");
                if(null == wxUser){
                    wxUser=new TSysWxUser();
                }
                wxUser.setUserid(username);
                wxUser.setPwd(password);
                wxUser.setOpenid(openid);
                log.info("-------------开始保存");
                wxService.save(wxUser);
                log.info("---------------保存结束");
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//默认全部为admin权限
                authorities.add(new Openid(openid));
                authorities.add(new CasTgc(tgt));
                return new UsernamePasswordAuthenticationToken(authentication.getName(),password,authorities);
            }
            throw new BadCredentialsException("账号或者密码不正确！");
        }
        throw new BadCredentialsException("为查找到登录用户！账号可能被禁用！");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
