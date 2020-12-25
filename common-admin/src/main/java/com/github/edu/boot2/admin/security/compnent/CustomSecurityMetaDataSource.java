package com.github.edu.boot2.admin.security.compnent;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.boot2.admin.entity.TSysFunction;
import com.github.edu.boot2.admin.entity.TSysMenu;
import com.github.edu.boot2.admin.entity.TSysRole;
import com.github.edu.boot2.admin.service.ISysFunctionService;
import com.github.edu.boot2.admin.service.ISysMenuService;
import com.github.edu.boot2.admin.service.ISysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Spring Security 权限资源管理
 * FilterInvocationSecurityMetadataSource ：Spring Security 权限资源管理接口类
 * Create by IntelliJ IDEA
 * 它的主要责任就是当访问一个url时返回这个url所需要的访问权限。
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/20
 */
@Component
@Slf4j
public class CustomSecurityMetaDataSource implements FilterInvocationSecurityMetadataSource {

    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Autowired
    private ISysMenuService sysMenuService;


    @Autowired
    private ISysFunctionService functionService;

    @Autowired
    private ISysRoleService roleService;

    /**
     * 无需校验的地址
     */
    @Value("${server.custom.security.common}")
    private String [] commons;


    /**
     * Accesses the {@code ConfigAttribute}s that apply to a given secure object.
     * 返回本次访问需要的权限，可以有多个权限。
     * 在上面的实现中如果没有匹配的url直接返回null，
     * 也就是没有配置权限的url默认都为白名单，想要换成默认是黑名单只要修改这里即可。
     *
     * @param object the object being secured
     * @return the attributes that apply to the passed in secured object. Should return an
     * empty collection if there are no applicable attributes.
     * @throws IllegalArgumentException if the passed object is not of a type supported by
     *                                  the <code>SecurityMetadataSource</code> implementation
     */
    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        Set<ConfigAttribute> set = new HashSet<>();
        // 获取请求地址
        String requestUrl = ((FilterInvocation) object).getRequest().getRequestURI();
        log.info("----request uri:" + requestUrl);
        if (StringUtils.isNotBlank(requestUrl)) {
            if (requestUrl.indexOf("/rest/api") > 0) {// 接口 验证
                List<TSysFunction> list = functionService.queryAllApi();//查询全部接口
                if (null != list && list.size() > 0) {
                    for (TSysFunction function : list) {
                        if (matchPath(function.getPath(), requestUrl)) {
                            List<TSysRole> roles=roleService.queryAllByFunctionId(function.getId());
                            if(null!=roles&&roles.size()>0){
                                roles.forEach(tSysRole -> {
                                    SecurityConfig securityContext=new SecurityConfig("ROLE_"+tSysRole.getCode().toUpperCase());
                                    set.add(securityContext);
                                });
                            }
                        }
                    }
                }
            } else {
                List<TSysMenu> list = sysMenuService.getAllMenuToSecurityDataSource();//菜单地址验证
                if (null != list && list.size() > 0) {
                    for (TSysMenu tSysMenu : list) {
                        if (matchPath(tSysMenu.getPath(), requestUrl)) {
                            List<TSysRole> roles=roleService.queryAllByMenuId(tSysMenu.getId());
                            if(null!=roles&&roles.size()>0){
                                roles.forEach(tSysRole -> {
                                    SecurityConfig securityConfig=new SecurityConfig("ROLE_"+tSysRole.getCode().toUpperCase());
                                    set.add(securityConfig);
                                });
                            }
                        }
                    }
                }
            }
            if(null!=commons&&commons.length>0){
                for (String str:commons){
                    if(matchPath(str,requestUrl)){
                        SecurityConfig securityConfig=new SecurityConfig("ROLE_COMMON");
                        set.add(securityConfig);
                    }
                }
            }
        }
        if(ObjectUtils.isEmpty(set)){
            SecurityConfig securityConfig=new SecurityConfig("ROLE_LOGIN");
            set.add(securityConfig);
        }
        return set;
    }

    /**
     * If available, returns all of the {@code ConfigAttribute}s defined by the
     * implementing class.
     * <p>
     * This is used by the {@link AbstractSecurityInterceptor} to perform startup time
     * validation of each {@code ConfigAttribute} configured against it.
     *
     * @return the {@code ConfigAttribute}s or {@code null} if unsupported
     */
    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    /**
     * Indicates whether the {@code SecurityMetadataSource} implementation is able to
     * provide {@code ConfigAttribute}s for the indicated secure object type.
     *
     * @param clazz the class that is being queried
     * @return true if the implementation can process the indicated class
     */
    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    public boolean matchPath(String pattern, String path) {
        if (StringUtils.isNotBlank(path) && StringUtils.isNotBlank(pattern)) {
            return antPathMatcher.match(pattern + "/**", path);
        }
        return false;
    }
}
