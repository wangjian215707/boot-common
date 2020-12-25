package com.github.edu.security.login.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.prefs.BackingStoreException;

/**
 * 用户登陆失败处理类
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
 */
@Slf4j
public class MySimpleUrlAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Autowired
    private MyCustomUserLockNum lockNum;

    private String defaultFailureUrl;//默认失败请求地址
    private boolean forwardToDestination = false;//转发到默认地址
    private boolean allowSessionCreation = true;//设置是否允许创建会话
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public MySimpleUrlAuthenticationFailureHandler() {
    }

    public MySimpleUrlAuthenticationFailureHandler(String defaultFailureUrl) {
        setDefaultFailureUrl(defaultFailureUrl);
    }

    /**
     * Performs the redirect or forward to the {@code defaultFailureUrl} if set, otherwise
     * returns a 401 error code.
     * <p>
     * If redirecting or forwarding, {@code saveException} will be called to cache the
     * exception for use in the target view.
     */
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        if (defaultFailureUrl == null) {
            log.debug("No failure URL set, sending 401 Unauthorized error");

            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Authentication Failed: " + exception.getMessage());
        }
        else {
            String username=request.getParameter("username");//获取登录失败错误账号
            int num=lockNum.getUserLockMap(request,username);
            if(num<5){
                lockNum.setUserLockNum(request,username,num+1);
            }
            log.info(exception.getMessage());
            if(!StringUtils.isEmpty(exception.getMessage())&&!"验证码错误！".equals(exception.getMessage())){
                if(num>=5){
                    request.getSession().removeAttribute(username+"_lock_user");
                    exception.addSuppressed(new BackingStoreException("您已经输错密码超过5次，请1小时后登录！"));
                }else {
                    exception.addSuppressed(new BackingStoreException("您已经连输输错密码"+num+"次，5次后账号将会锁定一小时"));
                }
            }
            saveException(request, exception);
            if (forwardToDestination) {
                log.debug("Forwarding to " + defaultFailureUrl);

                request.getRequestDispatcher(defaultFailureUrl)
                        .forward(request, response);
            }
            else {
                log.debug("Redirecting to " + defaultFailureUrl);
                redirectStrategy.sendRedirect(request, response, defaultFailureUrl);
            }
        }
    }

    protected final void saveException(HttpServletRequest request, AuthenticationException exception) {
        if (this.forwardToDestination) {
            request.setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
        } else {
            HttpSession session = request.getSession(false);
            if (session != null || this.allowSessionCreation) {
                request.getSession().setAttribute("SPRING_SECURITY_LAST_EXCEPTION", exception);
            }
        }

    }

    public void setDefaultFailureUrl(String defaultFailureUrl) {
        Assert.isTrue(UrlUtils.isValidRedirectUrl(defaultFailureUrl), () -> {
            return "'" + defaultFailureUrl + "' is not a valid redirect URL";
        });
        this.defaultFailureUrl = defaultFailureUrl;
    }

    protected boolean isUseForward() {
        return this.forwardToDestination;
    }

    public void setUseForward(boolean forwardToDestination) {
        this.forwardToDestination = forwardToDestination;
    }

    public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }

    protected RedirectStrategy getRedirectStrategy() {
        return this.redirectStrategy;
    }

    protected boolean isAllowSessionCreation() {
        return this.allowSessionCreation;
    }

    public void setAllowSessionCreation(boolean allowSessionCreation) {
        this.allowSessionCreation = allowSessionCreation;
    }
}
