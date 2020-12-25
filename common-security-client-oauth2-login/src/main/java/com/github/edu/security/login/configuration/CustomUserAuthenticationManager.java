package com.github.edu.security.login.configuration;

import com.github.edu.security.login.TSysUserService;
import com.github.edu.security.login.entity.TSysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 自定义用户身份认证
 */
@Slf4j
public class CustomUserAuthenticationManager implements AuthenticationProvider {

    @Autowired
    private TSysUserService userService;


    /**
     * 这里处理用户认证过程，可自定义
     *
     * @param authentication
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        List<GrantedAuthority> authorities = new ArrayList<>();
        boolean info = true;
        String username = authentication.getName();
        if (StringUtils.isEmpty(username)) {
            throw new BadCredentialsException("未获取的openid");
        }
        TSysUser tuser = userService.getUser(username);
        authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));//默认全部为admin权限
        if (null == tuser) {
            info = false;
            // 没有通过认证则抛出密码错误异常
            throw new BadCredentialsException("用户名不存在！");
        }
        return new UsernamePasswordAuthenticationToken(authentication.getName(), "000000", authorities);


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
