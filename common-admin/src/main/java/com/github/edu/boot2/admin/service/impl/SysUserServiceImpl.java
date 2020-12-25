package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.common.DBUtil;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysUserDao;
import com.github.edu.boot2.admin.entity.SecurityUser;
import com.github.edu.boot2.admin.entity.TSysRole;
import com.github.edu.boot2.admin.entity.TSysUser;
import com.github.edu.boot2.admin.rest.exception.CustomException;
import com.github.edu.boot2.admin.service.ISysRoleService;
import com.github.edu.boot2.admin.service.ISysUserService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import com.github.edu.boot2.admin.util.ResultStatusEnum;
import com.github.edu.boot2.admin.util.SqlEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/1
 */
@Slf4j
public class SysUserServiceImpl extends BaseAbstractService<TSysUser, Long> implements ISysUserService, UserDetailsService {

    @Autowired
    private ISysUserDao dao;

    @Value("${server.custom.security.encode}")
    private Integer encode;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private DBUtil dbUtil;

    @Override
    public TSysUser getUser(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            List<TSysUser> list =dbUtil.getAllBeanList(SqlEnum.QUERY_SQL_ENTITY_USER_LOGIN,TSysUser.class,userId);
            if (null != list && list.size() > 0) {
                return list.get(0);
            }
        }
        throw new CustomException(ResultStatusEnum.NOT_PARAM_USER_OR_ERROR_PWD.getCode(),ResultStatusEnum.NOT_PARAM_USER_OR_ERROR_PWD.getMsg());
    }

    @Override
    public TSysUser getSecurityUser(String userId) {
        if(StringUtils.isNotBlank(userId)){
            List<TSysUser> list=dao.getAllByUserid(userId,ConstantEnum.ENUM_STATE_NO.getNum(),ConstantEnum.ENUM_STATE_QY.getNum());
            if(null!=list&&list.size()>0){
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
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        TSysUser tSysUser = getSecurityUser(userId);
        if (tSysUser == null) {
            log.error("user login error : userId not find ! 没有发现用户登录账号！");
            throw new UsernameNotFoundException("UserName " + userId + " not found");
        }
        List<TSysRole> list = roleService.queryAllByUserId(userId);
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if(null!=tSysUser&& null!=tSysUser.getYhsx() && ConstantEnum.ENUM_TYPE_USER_SYSADMIN.getNum()==tSysUser.getYhsx()){
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        if (null != list && list.size() > 0) {
            for (TSysRole sysRole:list){
                authorities.add(new SimpleGrantedAuthority("ROLE_"+sysRole.getName().toUpperCase()));
            }
        }
        return new SecurityUser(tSysUser,authorities);
    }

}
