package com.github.edu.security.login.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/9
 * Time: 23:15
 */
public class SecurityUser implements UserDetails {

    private TSysUser tuser;

    private  Collection<GrantedAuthority> authorities;

    public SecurityUser(TSysUser tuser,Collection<GrantedAuthority> authorities){
        this.tuser=tuser;
        this.authorities=authorities;
    }
    public Collection<GrantedAuthority> getAuthorities() {
        if(null!=tuser&&"admin".equals(tuser.getUserId())){
            this.authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
        }
        return this.authorities;
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
            return tuser.getUserId();
        }
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        if(null!=tuser){
            if(tuser.getIsLocked() == 0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(null!=tuser){
            if(tuser.getIsEnable()==1){
                return true;
            }
        }
        return false;
    }
}
