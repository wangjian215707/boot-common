package com.github.edu.security.login.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.http.util.HttpUtil;
import com.github.edu.security.login.company.entity.UserInformation;
import com.github.edu.security.login.company.service.AccessTokenService;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.SecurityUser;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.entity.TSysWxUser;
import com.github.edu.security.login.service.UserService;
import com.github.edu.security.login.service.UserWxService;
import com.github.edu.security.login.util.CookieManagerUtil;
import com.github.edu.security.login.util.TGTDemo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.github.edu.security.login.util.CookieManagerUtil.getDecodedCookieValue;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-9-20
 */
@Controller
@Slf4j
public class UserLoginController {

    @Autowired
    private UserService service;
    @Autowired
    private UserWxService wxService;
    @Value("${weixin.custom.corpid}")
    private String corpid;
    @Value("${weixin.custom.agentId}")
    private String customAgentId;
    @Value("${weixin.custom.corpsecret}")
    private String customCorpsecret;
    @Value("${weixin.custom.domain}")
    private String domainstr;
    @Value("${weixin.custom.cas-domain}")
    private String casDomain;
    @Autowired
    private AccessTokenService tokenService;
    @Autowired
    private UserLoginInterfaceDomain domain;
    @Autowired
    private HttpUtil httpUtil;
    @GetMapping("/login")
    public String login(HttpSessionRequestCache sessionRequestCache, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap){
        SavedRequest savedRequest = sessionRequestCache.getRequest(request, response);
        String redirectUrl ="/uc/index/index";
        if (null != savedRequest) {
            redirectUrl = savedRequest.getRedirectUrl();
        }
        TSysWxUser user = null;
        String code = "";
        if (redirectUrl.indexOf("?code=") != -1) {
            code = redirectUrl.substring(redirectUrl.lastIndexOf("?code=") + 6, redirectUrl.length());
            redirectUrl = redirectUrl.substring(0, redirectUrl.lastIndexOf("?code"));
        }
        if (StringUtils.isEmpty(code)) {
            StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?");
            url.append("appid=" + corpid);
            try {
                url.append("&redirect_uri=" + URLEncoder.encode(redirectUrl, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url.append("&response_type=code&scope=snsapi_userinfo");
            url.append("&agentid=" + customAgentId+"#wechat_redirect");
            return "redirect:" + url.toString();
        }
        String access_token = tokenService.getAccessToken(customCorpsecret);
        System.out.println(access_token);
        if (StringUtils.isEmpty(access_token)) {
            access_token = tokenService.getNewAccessToken(customCorpsecret);
        }
        String ticked_token = tokenService.getUserTicket(access_token, code);
        log.info("ticked_token："+ticked_token);
        if (!StringUtils.isEmpty(ticked_token)) {//开始获取用户身份信息
            Map<String, Object> map = new HashMap<>();
            map.put("user_ticket", ticked_token);
            String info = httpUtil.responseJsonServicePost("https://qyapi.weixin.qq.com/cgi-bin/user/getuserdetail?access_token=" + access_token, map,null);
            if (!StringUtils.isEmpty(info)) {
                UserInformation userInformation= JsonUtils.toCollection(info, new TypeReference<UserInformation>(){});
                if (null != userInformation) {
                    if (service.openIdLogin(request, response, userInformation.getUserid())) {
                        return "redirect:" + redirectUrl;
                    }
                    user = new TSysWxUser();
                    String img = userInformation.getAvatar();
                    String openid = userInformation.getUserid();
                    String username = userInformation.getName();
                    if (!StringUtils.isEmpty(img)) {
                        if (img.indexOf("/0") != -1) {
                            img.replace("/0", "/100");
                        } else {
                            img = img + "100";
                        }
                    }
                    user.setImage(img);
                    user.setOpenid(openid);
                    user.setName(username);
                    user = wxService.save(user);
                    log.info("查看库里是否有这个用户" + openid);
                }
            }
        }
        if (null == user) {
            user = new TSysWxUser();
        }
        modelMap.put("entity", user);
        return "mobile/login/index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login";
    }
    @GetMapping("/error")
    public String error(){
        return "index/error";
    }
}
