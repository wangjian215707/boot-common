package com.github.edu.security.login.controller;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.client.common.controller.BaseController;
import com.github.edu.security.login.TSysUserService;
import com.github.edu.security.login.util.ICECookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/9
 * Time: 22:30
 */
@Slf4j
@Controller
public class UserLoginController extends BaseController {

    @Value("${server.custom.verification-code}")
    public boolean verificationCode;

    @Autowired
    private TSysUserService service;

    @RequestMapping("/login")
    public String userLogin(HttpSessionRequestCache sessionRequestCache,
                            HttpServletRequest request,
                            HttpServletResponse response,
                            ModelMap modelMap) {
        modelMap.put("verificationCode",verificationCode);
        SavedRequest savedRequest = sessionRequestCache.getRequest(request,response);
        String redirectUrl = "/uc/index/index";
        if (null != savedRequest) {
            redirectUrl = savedRequest.getRedirectUrl();
        }
        if(service.checkedLogin(request,response)){
            return "redirect:"+redirectUrl;
        }
        return "index/login/index";
    }

    @RequestMapping("/")
    public String toIndex(HttpServletRequest request) {
        String userId = getCurrentUserId();
        log.info("系统用户：" + userId);
        if (StringUtils.isNotBlank(userId)) {
            return "redirect:/uc/user/index";
        }
        return "redirect:/s/index";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {

        return "index/index";

    }
    @Value("${server.custom.ice.domain}")
    private String domain;

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
            ICECookieUtil.removeCookie(response,domain);
        }
        return "redirect:/s/index";
    }
}
