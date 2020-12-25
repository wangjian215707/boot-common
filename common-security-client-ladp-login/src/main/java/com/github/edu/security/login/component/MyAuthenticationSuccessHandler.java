package com.github.edu.security.login.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆成功后处理类
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
 */
@Component
@Slf4j
public class MyAuthenticationSuccessHandler extends
        SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private MyCustomUserLockNum lockNum;

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        // 认证成功后，获取用户信息并添加到session中
        log.info(request.getParameter("username"));
        lockNum.removeLockNum(request,request.getParameter("username"));
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
