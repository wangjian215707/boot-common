package com.github.edu.security.login.interceptor;

import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.service.UserService;
import com.github.edu.security.login.util.CookieManagerUtil;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import com.wiscom.is.SSOToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class IceInterceptor implements HandlerInterceptor {

    @Value("${server.custom.ice.proxy}")
    private String proxy;
    @Value("${server.custom.ice.username}")
    private String userName;
    @Value("${server.custom.ice.password}")
    private String pwd;
    @Value("${server.custom.ice.domain}")
    private String domain;
    @Value("${server.custom.ice.file}")
    private String file;
    @Value("${server.custom.ice.file1}")
    private String file1;

    @Autowired
    private UserService service;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        SecurityContextImpl securityContextImpl = (SecurityContextImpl) httpServletRequest
                .getSession().getAttribute("SPRING_SECURITY_CONTEXT");
        if (null != securityContextImpl) {
            String password = null;
            String userName = null;
            if (securityContextImpl.getAuthentication() != null) {
                password = (String) securityContextImpl.getAuthentication().getCredentials();
                userName = securityContextImpl.getAuthentication().getName();
            }
            if (!StringUtils.isEmpty(password) && !StringUtils.isEmpty(userName)) {
                IdentityFactory factory = IdentityFactory.createFactory(file);
                IdentityManager im = factory.getIdentityManager();
                SSOToken token = im.createStoken(userName, password);
                CookieManagerUtil.addCookie(httpServletResponse, token.getTokenValue(), domain);
                ((CredentialsContainer) securityContextImpl.getAuthentication()).eraseCredentials();//清除密码
            }
            if (StringUtils.isEmpty(password)) {//pc端访问
                String cookie = CookieManagerUtil.getDecodedCookieValue(httpServletRequest, "iPlanetDirectoryPro");
                if (!StringUtils.isEmpty(cookie)) {
                    IdentityFactory factory = IdentityFactory.createFactory(file);
                    IdentityManager im = factory.getIdentityManager();
                    String userid = im.getCurrentUser(cookie);
                    if (StringUtils.isEmpty(userid)) {
                        httpServletResponse.sendRedirect("/logout");
                        return true;
                    }
                }else {
                    httpServletResponse.sendRedirect("/logout");
                    return true;
                }
            }
            TSysUser user = (TSysUser) httpServletRequest.getSession().getAttribute("SESSION_USER_BEAN");
            if (!StringUtils.isEmpty(userName)) {
                if (null == user) {
                    user = service.getUser(userName.toUpperCase());
                    httpServletRequest.getSession().setAttribute("SESSION_USER_BEAN", user);
                }
                if (null != user && !StringUtils.isEmpty(user.getYhsf()) && 3 == user.getYhsf()) {
                    httpServletResponse.sendRedirect("http://erp.sdwz.cn");
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
