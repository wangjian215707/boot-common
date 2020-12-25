package com.github.edu.security.login.security;

import com.github.edu.security.login.service.ICustomSecurityContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import java.util.Collection;

/**
 * Spring Security 权限资源管理
 * FilterInvocationSecurityMetadataSource ：Spring Security 权限资源管理接口类
 * Create by IntelliJ IDEA
 * 它的主要责任就是当访问一个url时返回这个url所需要的访问权限。
 * 用户：王建
 * 日期：2019/8/26
 */
@Slf4j
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {

    @Autowired
    private ICustomSecurityContextService service;
    /**
     * 返回本次访问需要的权限，可以有多个权限。
     * 在上面的实现中如果没有匹配的url直接返回null，
     * 也就是没有配置权限的url默认都为白名单，想要换成默认是黑名单只要修改这里即可。
     * @param o
     * @return
     * @throws IllegalArgumentException
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) o;
        String requestURI = fi.getRequest().getRequestURI();
        log.info("----requestURI:"+requestURI);
        Collection<ConfigAttribute> configAttributes=service.getCollection(requestURI);
        return configAttributes;
    }

    /**
     * 返回了所有定义的权限资源，
     * Spring Security会在启动时校验每个ConfigAttribute是否配置正确，如不需要校验直接返回null。
     * @return
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * 返回类对象是否支持校验，web项目一般使用FilterInvocation来判断
     * ,或者直接返回true。
     * @param aClass
     * @return
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
