package com.github.edu.security.login.interceptor;
import com.github.edu.security.login.service.GetCasTGTService;
import com.github.edu.security.login.util.CookieManagerUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/4
 * Time: 12:25
 */
@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    @Value("${weixin.custom.domain}")
    private String domain;

    @Autowired
    private GetCasTGTService tgtService;

    @Value("${weixin.custom.cas-domain}")
    private String casDomain;

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
           /* if (!StringUtils.isEmpty(password) && !StringUtils.isEmpty(userName)) {//用户刚登录
                String tgt = tgtService.getTicketGrantingTicket(password, userName,httpServletRequest);
                if(!StringUtils.isEmpty(tgt)){
                    CookieManagerUtil.addCookie(httpServletResponse,casDomain,tgt,domain);
                }
                ((CredentialsContainer) securityContextImpl.getAuthentication()).eraseCredentials();//清除密码
                return true;
            }*/
        }
        return true;
    }
}
