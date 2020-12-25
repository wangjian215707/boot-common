package com.github.edu.security.login.controller;

import com.github.edu.client.common.controller.BaseController;
import com.github.edu.security.login.IUserWxUserService;
import com.github.edu.security.login.UserService;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.weixin.gzh.common.entity.AccessTokenEntity;
import com.github.edu.weixin.gzh.common.entity.TSysWxUser;
import com.github.edu.weixin.gzh.common.service.AccessTokenWebService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/9
 * Time: 22:30
 */
@Slf4j
@Controller
public class UserLoginController extends BaseController {

    @Value("${weixin.custom.appid}")
    private String appId;

    @Value("${server.custom.cas.client.host}")
    private String clientUrl;

    @Autowired
    private AccessTokenWebService webService;

    @Autowired
    private IUserWxUserService userService;

    @Autowired
    private UserService service;

    @Autowired
    private UserLoginInterfaceDomain domain;

    @GetMapping("/login")
    public String login(HttpSessionRequestCache sessionRequestCache, HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        SavedRequest savedRequest = sessionRequestCache.getRequest(request, response);
        String redirectUrl ="http://wxfw.txgz.cn/uc/index/index";
        String code ="";
        TSysWxUser user = null;
        if (null != savedRequest) {
            redirectUrl = savedRequest.getRedirectUrl();
            //TODO::这里出现error有可能是因为原来绑定信息中密码错误导致的error
            log.info("-------test:"+redirectUrl);
            if(redirectUrl.indexOf("error")!=-1){
                redirectUrl ="http://wxfw.txgz.cn/uc/index/index";
            }
        }
        if (redirectUrl.indexOf("?code=") != -1) {
            code = redirectUrl.substring(redirectUrl.lastIndexOf("?code=") + 6, redirectUrl.length());
            redirectUrl = redirectUrl.substring(0, redirectUrl.lastIndexOf("?code"));
        }
        if (StringUtils.isEmpty(code)) {
            StringBuffer url = new StringBuffer("https://open.weixin.qq.com/connect/oauth2/authorize?");
            url.append("appid=" + appId);
            try {
                log.info("redirect:"+redirectUrl);
                url.append("&redirect_uri=" + URLEncoder.encode(redirectUrl, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            url.append("&response_type=code&scope=snsapi_userinfo&state=apexsoft#wechat_redirect");
            return "redirect:" + url.toString();
        }
        String access_token = null;
        String openid = null;
        AccessTokenEntity accessTokenEntity = webService.getAccessToken(code);
        if(null !=accessTokenEntity && !StringUtils.isEmpty(accessTokenEntity.getAccess_token())&&!StringUtils.isEmpty(accessTokenEntity.getOpenid())){
            access_token = accessTokenEntity.getAccess_token();
            openid = accessTokenEntity.getOpenid();
            if (service.openIdLogin(request, response, openid)) {
                return "redirect:" + redirectUrl;
            }
            user = new TSysWxUser();
            user.setOpenid(openid);
            user = userService.save(user);

        }
        log.info(access_token);
        if (null == user) {
            user = new TSysWxUser();
        }
        modelMap.put("entity",user);
        return "mobile/login/index";
    }
}
