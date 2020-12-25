package com.github.edu.client.common.interceptor;

import com.github.edu.client.common.component.BaseContextHandler;
import com.github.edu.client.common.entity.TBsdtWxUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-24
 */
@Slf4j
public class UserInformationInterceptor extends HandlerInterceptorAdapter {
    @NotNull
    @Value("${server.custom.wx.enabled:false}")
    private boolean enabled;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            if(null!=authentication){
                String currentUserName = authentication.getName();
                BaseContextHandler.setUserID(currentUserName);
                request.getSession().setAttribute("userID",currentUserName);
                log.info("----------------:"+currentUserName);
            }
        }
        if(enabled){
            TBsdtWxUser wxUser=(TBsdtWxUser) request.getSession().getAttribute("wxUser");
            if(null!=wxUser){
                if(!StringUtils.isEmpty(wxUser.getOpenid())){
                    BaseContextHandler.setWxOpenId(wxUser.getOpenid());
                }
                if(!StringUtils.isEmpty(wxUser.getImage())){
                    BaseContextHandler.setWxImageUrl(wxUser.getImage());
                }
            }
        }
        return super.preHandle(request,response,handler);
    }
}
