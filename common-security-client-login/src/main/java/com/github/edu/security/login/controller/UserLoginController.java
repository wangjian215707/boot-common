package com.github.edu.security.login.controller;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.client.common.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("/login")
    public String userLogin(HttpServletRequest request) {
        return super.index(request, "index/login/index");
    }

    @RequestMapping("/")
    public String toIndex(HttpServletRequest request) {
        String userId= getCurrentUserId();
        log.info("系统用户："+userId);
        if(StringUtils.isNotBlank(userId)){
            return "redirect:/uc/portal/index";
        }
        return "index/index";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {
        return super.index(request,"index/index");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/s/index";
    }
}
