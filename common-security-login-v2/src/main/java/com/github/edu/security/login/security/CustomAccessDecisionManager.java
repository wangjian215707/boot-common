package com.github.edu.security.login.security;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import java.util.Collection;
import java.util.Iterator;

/**
 * 权限决策管理，用于判断用户是否具有
 * 访问当前url的权限
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/8/28
 */
public class CustomAccessDecisionManager implements AccessDecisionManager {



    /**
     * @param authentication 包含当前登陆用户的基本信息，包括权限信息（角色信息）
     *                       对应UserDetailsService中设置的authorities
     * @param o              FilterInvocation对象，可以得到request等web资源
     * @param collection     是本次访问需要的权限（权限列表）。
     * @throws AccessDeniedException
     * @throws InsufficientAuthenticationException
     */
    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection)
            throws AccessDeniedException, InsufficientAuthenticationException {
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            if (authentication == null) {
                throw new AccessDeniedException("当前访问没有权限");
            }
            ConfigAttribute configAttribute = iterator.next();
            String needCode = configAttribute.getAttribute();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            for (GrantedAuthority authority : authorities) {
                if("ROLE_ADMIN".equals(authority.getAuthority())){
                    return;
                }
                if (StringUtils.equals(authority.getAuthority(), "ROLE_" + needCode)) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("当前访问没有权限");
    }

    public boolean supports(ConfigAttribute attribute) {
        return true;
    }

    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}
