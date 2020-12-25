package com.github.edu.security.login.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.http.util.HttpUtil;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.client.common.controller.BaseController;
import com.github.edu.client.common.util.ICECookieUtil;
import com.github.edu.security.login.TSysUserService;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.JsonUser;
import com.github.edu.security.login.entity.SecurityUser;
import com.github.edu.security.login.entity.TSysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/9
 * Time: 22:30
 */
@Slf4j
@Controller
public class UserLoginController extends BaseController {

    @Value("${server.custom.cas.server.host}")
    private String serverHost;

    @Value("${server.custom.cas.client.host}")
    private String clientHost;

    @Value("${server.custom.cas.client.key}")
    private String key;

    @Value("${server.custom.cas.client.secret}")
    private String secret;

    @Autowired
    private HttpUtil httpUtil;

    @Autowired
    private UserLoginInterfaceDomain domain;

    @GetMapping("/s/oauth2/login")
    public String oauth2Login(HttpServletRequest request,
                              @RequestParam(name = "code", required = false) String code,
                              @RequestParam(name = "state", required = false) String state,
                              HttpServletResponse response) {
        log.info("---code"+code);
        if(StringUtils.isBlank(code)){
            return "redirect:" + serverHost + "/sso/oauth2/authorize?response_type=code&redirect_uri="
                    + clientHost + "/s/oauth2/login&client_id=" + key;
        }
        //再根据code获取id_token
        String url = serverHost + "/sso/oauth2/token?grant_type=authorization_code&scope=openid&redirect_uri="
                + clientHost + "/s/oauth2/login&client_id=" + key + "&client_secret=" + secret +
                "&code=" + code;
        String values = httpUtil.responseJsonServicePost(url,"application/x-www-form-urlencoded");
        log.info("------values=" + values);
        if (StringUtils.isNotBlank(values)) {
            JsonUser jsonUser = JsonUtils.toCollection(values, new TypeReference<JsonUser>() {
            });
            if (null != jsonUser) {
                String id = jsonUser.getId_token();
                if(StringUtils.isNotBlank(id)){
                    DecodedJWT jwt = JWT.decode(id);
                    Map<String, Claim> map = jwt.getClaims();
                    String name=map.get("name").asString();
                    String userId=map.get("account").asString();
                    log.info(userId);
                    log.info(name);
                    if(StringUtils.isNotBlank(userId)){
                        TSysUser tSysUser=null;
                        String json=domain.getUserLoginInformation(userId);
                        if(StringUtils.isNotBlank(json)){
                            JsonEntity<TSysUser> jsonEntity= JsonUtils.toCollection(json, new TypeReference<JsonEntity<TSysUser>>() {
                            });
                            if(null!=jsonEntity){
                                tSysUser=jsonEntity.getData();
                            }
                        }
                        if(null!=tSysUser){
                            log.info("----获取用户基本信息成功！"+tSysUser.getUserid());
                            ICECookieUtil.addCookie(response,".jsit.edu.cn",tSysUser.getUserid());
                            UserDetails userDetails = new SecurityUser(tSysUser);
                            Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails.getUsername(), "000000", userDetails.getAuthorities());
                            SecurityContextHolder.getContext().setAuthentication(authentication);
                            HttpSession session = request.getSession(true);
                            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
                            return "redirect:/uc/user/index";
                        }
                        log.error("-------用户不存在");
                    }
                }
                log.error("----:获取token失败！");
            }
        }
        return "index/index";
    }

    @RequestMapping("/login")
    public String userLogin(HttpServletRequest request) {
        return "redirect:" + serverHost + "/sso/oauth2/authorize?response_type=code&redirect_uri="
                + clientHost + "/s/oauth2/login&client_id=" + key;
    }

    @RequestMapping("/")
    public String toIndex(HttpServletRequest request) {
        String userId = (String) request.getSession().getAttribute("userID");
        log.info("系统用户：" + userId);
        if (StringUtils.isNotBlank(userId)) {
            return "redirect:/uc/user/index";
        }
        return "index/index";
    }

    @RequestMapping("/index")
    public String index(HttpServletRequest request) {

        return super.index(request, "index/index");

    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:"+serverHost+"/sso/logout?redirect_uri="+clientHost+"/s/index";
    }

}
