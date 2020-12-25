package com.github.edu.security.login.configuration;

import com.github.edu.security.login.entity.Openid;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.entity.TSysWxUser;
import com.github.edu.security.login.service.UserService;
import com.github.edu.security.login.service.UserWxService;
import com.github.edu.security.login.service.impl.LdapService;
import com.github.edu.security.login.util.DigestUtil;
import com.wiscom.is.IdentityFactory;
import com.wiscom.is.IdentityManager;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

/**
 * 自定义用户认证
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/4
 * Time: 11:51
 */
@Slf4j
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
    private UserService service;

    @Autowired
    private UserWxService wxService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        String username = authentication.getName();
        boolean info = false;
        if (StringUtils.isEmpty(username)) {
            throw new BadCredentialsException("用户名登录账号不能为空！");
        }
        String password = (String) authentication.getCredentials();
        String openid = "";
        List<Openid> list = (List<Openid>) authentication.getAuthorities();
        if (null != list && list.size() > 0) {
            openid = list.get(0).getAuthority();
            log.info("认证openid：" + openid);
        }
        if (StringUtils.isEmpty(password)) {
            throw new BadCredentialsException("登录密码不能为空！");
        }
        if (StringUtils.isEmpty(openid)) {
            throw new BadCredentialsException("未获取到用户openid！");
        }
        TSysUser user = service.getUser(username);
        if (null != user) {
            log.info("认证用户信息" + user.getUserid());
            if (user.getYhsf() == 1) {//学生
                password = com.github.admin.edu.assembly.common.util.DigestUtil.sha1(password);
            } else if (user.getYhsf() == 2) {//教师
                password = com.github.admin.edu.assembly.common.util.DigestUtil.md5(password);
            }
            IdentityFactory factory = null;
            try {
                factory = IdentityFactory.createFactory(file);
                IdentityManager manager = factory.getIdentityManager();
                info = manager.checkPassword(user.getUserid(), password);
                if (info == false && user.getYhsf() == 2) {//只针对老师做二次验证
                    IdentityFactory factory1 = IdentityFactory.createFactory(file1);
                    IdentityManager manager1 = factory1.getIdentityManager();
                    info = manager1.checkPassword(user.getUserid(), password);
                    if (info) {
                        info = LdapService.updateUserPassword(proxy, userName, pwd, user.getUserid(), password);
                    }
                }
            } catch (Exception e) {
                throw new BadCredentialsException("链接ldap服务器异常");
            }
            log.info("登陆是否成功！");
            if(info){
                TSysWxUser wxUser=wxService.findById(openid);
                if(null==wxUser){
                    wxUser=new TSysWxUser();
                }
                wxUser.setUserid(username);
                wxUser.setPwd(password);
                wxUser.setOpenid(openid);
                wxUser.setCookie(DigestUtil.md5(password+openid));
                wxService.save(wxUser);
                log.info("保存成功！");
                authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//默认全部为admin权限
                authorities.add(new Openid(openid));
                return new UsernamePasswordAuthenticationToken(authentication.getName().toUpperCase(),password,authorities);
            }
        }
        throw new BadCredentialsException("账号或者密码不正确！");
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return UsernamePasswordAuthenticationToken.class.equals(aClass);
    }
}
