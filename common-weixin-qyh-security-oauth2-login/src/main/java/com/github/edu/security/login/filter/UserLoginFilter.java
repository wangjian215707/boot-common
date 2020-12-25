package com.github.edu.security.login.filter;

import com.github.edu.security.login.entity.Openid;
import lombok.extern.slf4j.Slf4j;
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
 * 自定义登录了拦截
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/4
 * Time: 11:36
 */
@Slf4j
public class UserLoginFilter extends AbstractAuthenticationProcessingFilter {

    private boolean postOnly = true;

    public UserLoginFilter() {
        super(new AntPathRequestMatcher("/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException {
        if (postOnly &&! request.getMethod().equals("POST")) {
            log.error("Authentication method not supported: "+request.getMethod());
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }
        String username = obtainUsername(request);
        String password = obtainPassword(request);
        String openid=request.getParameter("openid");
        if (username == null) {
            username = "";
        }
        if (password == null) {
            password = "";
        }
        if(StringUtils.isEmpty(username)){
            log.error("-------:user login name is null ");
            throw new BadCredentialsException("没有获取到登录账号！");
        }
        if(StringUtils.isEmpty(password)){
            log.error("-------:user login password is null ");
            throw new BadCredentialsException("没有获取到登录密码！");
        }
        log.info("---------openid:"+openid);
        if(StringUtils.isEmpty(openid)){
            openid="";
            log.error("-----------------:openId is null ");
            throw new BadCredentialsException("没有获取到openId！");
        }
        username = username.trim().toUpperCase();
        List<GrantedAuthority> list=new ArrayList<>();
        list.add(new Openid(openid));
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                username, password,list);
        // Allow subclasses to set the "details" property
        this.setDetails(request, authRequest);

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
