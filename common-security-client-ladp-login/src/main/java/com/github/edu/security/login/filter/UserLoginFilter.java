package com.github.edu.security.login.filter;

import com.github.edu.security.login.component.MyCustomUserLockNum;
import com.github.edu.security.login.entity.Openid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
 */
@Slf4j
public class UserLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private MyCustomUserLockNum lockNum;

    @Value("${server.custom.verification-code}")
    public boolean verificationCode;

    private boolean postOnly = true;

    public UserLoginFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (postOnly && !request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String openid = request.getParameter("openid");//如果微信端使用的话，
        String code = request.getParameter("code");//验证码功能
        if (!StringUtils.isEmpty(code)) {
            code = code.toUpperCase();
        }
        if (verificationCode) {//如果开启了验证码功能
            String key = (String) request.getSession().getAttribute("IMAGE_SESSION_KEY");
            if(!StringUtils.isEmpty(key)){
                key=key.toUpperCase();
            }
            log.info("---@@@@@@@@@@@@@@@@@-key:"+key);
            log.info("--@@@@@@@@@@@-code:"+code);
            if (StringUtils.isEmpty(code)) {
                throw new BadCredentialsException("没有获取到验证码验证码错误！");
            } else if (!StringUtils.isEmpty(key) && !code.equals(key)) {
                throw new BadCredentialsException("验证码错误！");
            }
        }
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        /*if (!StringUtils.isEmpty(username)) {
            int num = lockNum.getUserLockMap(request, username);
            if (num >= 5) {
                log.error(username + ":你已连续输错密码5次，请一小时后再试！");
                throw new BadCredentialsException("你已连续输错密码5次，请一小时后再试！");
            }
        }*/

        if (openid == null) {
            openid = "";
        }
        username = username.trim();
        List<GrantedAuthority> list = new ArrayList<>();
        list.add(new Openid(openid));
        /**
         * 封装认证信息
         */
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password, list);
        // Allow subclasses to set the "details" property 允许子类设置 认证细节信息
        this.setDetails(request, authRequest);
        //封装认证信息，并且设置认证过程，未进行认证
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected String obtainPassword(HttpServletRequest request) {
        return request.getParameter("password");
    }

    protected String obtainUsername(HttpServletRequest request) {
        return request.getParameter("username");
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    protected void setDetails(HttpServletRequest request,
                              UsernamePasswordAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }
}
