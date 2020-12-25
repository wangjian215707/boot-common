package com.github.edu.security.login.component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.DigestUtil;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.edu.client.common.entity.TBsdtWxUser;
import com.github.edu.security.login.TSysUserService;
import com.github.edu.security.login.domain.UserLoginInterfaceDomain;
import com.github.edu.security.login.entity.Openid;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.impl.LdapService;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
 */
public class CustomUserAuthenticationManager implements AuthenticationProvider {

    @Value("${server.custom.ice.proxy}")
    private String proxy;
    @Value("${server.custom.ice.username}")
    private String userName;
    @Value("${server.custom.ice.password}")
    private String pwd;
    @Value("${server.custom.ice.domain}")
    private String domain;
    @Value("${server.custom.ice.file}")
    private String file;
    @Value("${server.custom.ice.file1}")
    private String file1;

    @Autowired
    private TSysUserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        boolean info=false;
        String username=authentication.getName().toUpperCase();
        if(StringUtils.isEmpty(username)){
            throw new BadCredentialsException("用户名登录账号不能为空！");
        }
        String password= (String) authentication.getCredentials();
        String openid="";
        List<Openid> list= (List<Openid>) authentication.getAuthorities();
        if (null!=list&&list.size()>0){
            openid=list.get(0).getAuthority();
        }
        if(StringUtils.isEmpty(password)){
            throw new BadCredentialsException("登录密码不能为空！");
        }
        try {
            TSysUser user=userService.getUser(username);
            if(null!=user){
                /*if(user.getYhsf()==1){//学生
                    password=DigestUtil.sha1(password);
                }else if(user.getYhsf()==2) {//教师
                    password= DigestUtil.md5(password);
                }*/
                password=DigestUtil.md5(password).toLowerCase();
                IdentityFactory factory=IdentityFactory.createFactory(file);
                IdentityManager manager=factory.getIdentityManager();
                info=manager.checkPassword(user.getUserid(),password);
                /*if(info==false&&user.getYhsf()==2){//只针对老师做二次验证 只文正使用
                    IdentityFactory factory1=IdentityFactory.createFactory(file1);
                    IdentityManager manager1=factory1.getIdentityManager();
                    info=manager1.checkPassword(user.getUserid(),password);
                    if(info){
                        info= LdapService.updateUserPassword(proxy,userName,pwd,user.getUserid(),password);
                    }
                }*/
            }
           /* if(!StringUtils.isEmpty(username)&&!StringUtils.isEmpty(openid)){
                TBsdtWxUser wxUser=new TBsdtWxUser();
                wxUser.setOpenid(openid);
                wxUser.setUserid(username);
                wxUser.setPwd(password);
                wxUser.setCookie(DigestUtil.md5(openid+password));
                Map<String,Object> map=new HashMap<>();
                map.put("data",wxUser);
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//默认全部为admin权限
        authorities.add(new Openid(openid));
        if(info){//验证通过
            //验证通过，并且设置super.setAuthenticated(true);标记为已经通过认证
            /*public UsernamePasswordAuthenticationToken(Object principal, Object credentials,
                    Collection<? extends GrantedAuthority> authorities) {
                super(authorities);
                this.principal = principal;
                this.credentials = credentials;
                super.setAuthenticated(true); // must use super, as we override
            }*/
            return new UsernamePasswordAuthenticationToken(authentication.getName().toUpperCase(),password,authorities);
        }
        // 没有通过认证则抛出密码错误异常
        throw new BadCredentialsException("用户名或者密码错误！");
    }

    /**
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {

        return UsernamePasswordAuthenticationToken.class.equals(authentication);
    }
}
