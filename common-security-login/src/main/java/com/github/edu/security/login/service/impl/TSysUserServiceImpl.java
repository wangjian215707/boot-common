package com.github.edu.security.login.service.impl;

import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.admin.edu.assembly.common.util.DigestUtil;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.domain.TSysUserInformationDomain;
import com.github.edu.security.login.entity.SecurityUser;
import com.github.edu.security.login.entity.TSysUser;
import com.github.edu.security.login.service.TSysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/24
 * Time: 14:04
 */
@Slf4j
public class TSysUserServiceImpl extends BaseAbstractService<TSysUser,Long> implements TSysUserService, UserDetailsService {

    @Autowired
    private TSysUserInformationDomain domain;

    @Override
    public TSysUser saveOrUpdate(TSysUser tSysUser) {
        if(null!=tSysUser){
            if(null==tSysUser.getPassword()){
                tSysUser.setPassword(DigestUtil.md5("000000"));
            }
            return domain.save(tSysUser);
        }
        return tSysUser;
    }

    @Override
    public String delete(String ids) {
        return super.delete(ids,"Long");
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
        TSysUser user = getUser(s);
        if (user == null) {
            log.error("user login error : userId not find ! 没有发现用户登录账号！");
            throw new UsernameNotFoundException("UserName " + s + " not found");
        }
        SecurityUser securityUser = new SecurityUser(user);
        return securityUser;
    }

    public TSysUser getUser(String userId){
        if(StringUtils.isNotBlank(userId)){
            List<TSysUser> list=domain.getAllByUserid(userId);
            if(null!=list&&list.size()>0){
                return list.get(0);
            }
        }
        return null;
    }
}
