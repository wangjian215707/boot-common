package com.github.edu.security.login.service.impl;

import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.entity.DeleteMessage;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.security.login.dao.IRoleDao;
import com.github.edu.security.login.dao.ISysUserDao;
import com.github.edu.security.login.entity.SecurityUser;
import com.github.edu.security.login.entity.TSysRole;
import com.github.edu.security.login.entity.TSysRoleUser;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.service.ISysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
@Slf4j
public class SysUserServiceImpl extends BaseAbstractService<TSysUser, Long> implements ISysUserService, UserDetailsService {

    @Autowired
    private ISysUserDao dao;

    @Autowired
    private IRoleDao roleDao;

    @Value("${server.custom.security.encode}")
    private Integer encode;

    @Override
    public TSysUser getUser(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            List<TSysUser> list = dao.getAllByUserid(userId);
            if (null != list && list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }


    @Override
    public TSysUser saveOrUpdate(TSysUser tSysUser) {
        if (StringUtils.isBlank(tSysUser.getPassword())) {
            tSysUser.setPassword(new BCryptPasswordEncoder(encode).encode("000000"));
        }
        tSysUser = dao.save(tSysUser);
        return tSysUser;
    }

    @Override
    public String delete(String ids) {

        return super.delete(ids, "Long");
    }

    @Override
    public Map<String, Object> getAllMapByPage(TSysUser tSysUser, Integer current, Integer rows) {
        return null;
    }

    @Override
    public Map<String, Object> getAllMapByPage(TSysUser tSysUser, Integer current, Integer rows, String orders) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TSysUser tSysUser = getUser(s);
        if (tSysUser == null) {
            log.error("user login error : userId not find ! 没有发现用户登录账号！");
            throw new UsernameNotFoundException("UserName " + s + " not found");
        }
        List<TSysRole> list = roleDao.getAllByRoleUser(s);
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (s.equals("admin")) {//管理员
            authorities.add(new SimpleGrantedAuthority("ROLE_admin"));
        }
        if (null != list && list.size() > 0) {
            for (TSysRole sysRole:list){
                authorities.add(new SimpleGrantedAuthority("ROLE_"+sysRole.getName()));
            }
        }
        return new SecurityUser(tSysUser,authorities);
    }

}
