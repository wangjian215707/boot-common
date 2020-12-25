package com.github.edu.security.login.controller;

import com.github.edu.client.common.controller.BaseController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登陆
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/9
 * Time: 22:30
 */
@Controller
public class UserLoginController extends BaseController {

    @GetMapping("/login")
    public String userLogin(HttpServletRequest request) {
        return super.index(request, "index/login/index");
    }

    @GetMapping("/")
    public String toIndex() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(HttpServletRequest request) {
        return super.index(request,"index/index");
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }
}
