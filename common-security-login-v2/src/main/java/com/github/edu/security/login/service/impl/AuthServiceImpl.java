package com.github.edu.security.login.service.impl;

import com.github.edu.security.login.dao.IPermissionDao;
import com.github.edu.security.login.service.IAuthService;
import com.github.edu.security.login.service.ICustomSecurityContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
@Slf4j
@Service(value = "authServiceImpl")
public class AuthServiceImpl implements IAuthService {

    @Autowired
    private IPermissionDao permissionDao;

    @Autowired
    private ICustomSecurityContextService securityContextService;

    @Value("${server.custom.security.noauth}")
    private String[] noauth;

    /**
     * 权限校验
     *
     * @param request
     * @param authentication
     * @return
     */
    @Override
    public boolean isAccess(HttpServletRequest request, Authentication authentication) {

        /**
         * 1、未登录的情况下需要做判断和拦截
         *
        */
        Object obj=authentication.getPrincipal();
        if(null==obj||"anonymousUser".equals(obj)){//用户未登录
            return false;
        }
        for (GrantedAuthority authority:authentication.getAuthorities()){//如果是管理员角色
            if("ROLE_admin".equals(authority.getAuthority())){
                return true;
            }
        }
        /**
         * 3、配置无需验证权限的地址
         */
        if(null!=noauth&&noauth.length>0){
            for (String str:noauth){
                AntPathRequestMatcher matcher=new AntPathRequestMatcher(str);
                if (matcher.matches(request)){
                    return true;
                }
            }
        }
        /**
         * 4、匿名角色,需要事使用
         */
        if (authentication instanceof AnonymousAuthenticationToken){

        }
        /**
         * 5、通过request对象获取uri，根据uri 获取对应权限信息
         */
        Map<String, Collection<ConfigAttribute>> map=securityContextService.getCollectionMap();
        Collection<ConfigAttribute> configAttributes=null;
        if(null!=map&&map.size()>0){
            for (Iterator<String> it = map.keySet().iterator(); it.hasNext();){
                String path=it.next();
                AntPathRequestMatcher matcher=new AntPathRequestMatcher(path);
                if(matcher.matches(request)){
                    configAttributes=map.get(path);
                    break;
                }
            }
            if(null==configAttributes||configAttributes.size()==0){
                log.error(authentication.getName()+"-----没有获取到对应权限信息：");
                return false;
            }
        }
        /**
         * 6、将资源列表和当前登录账号权限信息进行比对
         */
        for (Iterator<ConfigAttribute> it= configAttributes.iterator();it.hasNext();){
            ConfigAttribute configAttribute=it.next();
            String role=configAttribute.getAttribute();
            for (GrantedAuthority authority:authentication.getAuthorities()){
                if(role.equals(authority.getAuthority())){
                    return true;
                }
            }
        }
        return false;
    }
}
