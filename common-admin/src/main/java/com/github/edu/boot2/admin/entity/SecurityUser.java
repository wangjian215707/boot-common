package com.github.edu.boot2.admin.entity;

import com.github.edu.boot2.admin.util.ConstantEnum;
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

    private  Collection<? extends GrantedAuthority> authorities;

    public SecurityUser(TSysUser tuser, Collection<? extends GrantedAuthority> authorities){
        this.tuser=tuser;
        this.authorities=authorities;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<? extends GrantedAuthority> list=this.authorities;
        return list;
    }

    @Override
    public String getPassword() {
        if(null!=tuser){
            return tuser.getPassword();
        }
        return null;
    }

    public TSysUser getUser(){
        return this.tuser;
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
        /*if(null!=tuser){
            if(tuser.getIsLocked() == ConstantEnum.ENUM_STATE_NO.getNum()){
                return true;
            }
        }*/
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        /*if(null!=tuser){
            if( tuser.getZt() == ConstantEnum.ENUM_STATE_QY.getNum()){
                return true;
            }
        }*/
        return true;
    }
}
