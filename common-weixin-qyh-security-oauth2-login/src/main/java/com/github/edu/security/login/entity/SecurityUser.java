package com.github.edu.security.login.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/9
 * Time: 23:15
 */
public class SecurityUser implements UserDetails {

    private TSysUser tuser;

    public SecurityUser(TSysUser tuser){
        this.tuser=tuser;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> list=new ArrayList<>();
        if(null!=tuser&&"admin".equals(tuser.getUserid())){
            list.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
        }else {
            list.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return list;
    }

    @Override
    public String getPassword() {
        if(null!=tuser){
            return tuser.getPassword();
        }
        return null;
    }

    @Override
    public String getUsername() {
        if(null!=tuser){
            return tuser.getUserid();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
