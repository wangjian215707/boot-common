package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.common.DBUtil;
import com.github.edu.boot2.admin.entity.SecurityUser;
import com.github.edu.boot2.admin.entity.TSysRole;
import com.github.edu.boot2.admin.entity.TSysUser;
import com.github.edu.boot2.admin.rest.exception.CustomException;
import com.github.edu.boot2.admin.service.ISysUserLoginService;
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 用户登陆处理类
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/13
 */
@Slf4j
public class SysUserLoginServiceImpl implements ISysUserLoginService, UserDetailsService {

    @Autowired
    private DBUtil dbUtil;

    @Value("${spring.data-type}")
    private String dataType;

    /**
     * 查询用户基本信息
     * @param userId
     * @return
     */
    @Override
    public TSysUser getUser(String userId) {
        if (StringUtils.isNotBlank(userId)) {
            String sql = SqlEnum.QUERY_SQL_ENTITY_USER_LOGIN.sql(dataType);
            log.debug("type:" + dataType + "   sql:" + sql);
            TSysUser tSysUser = dbUtil.queryFieldValue(sql, TSysUser.class, userId);
            return tSysUser;
        }
        throw new CustomException(ResultStatusEnum.NOT_PARAM_USER_OR_ERROR_PWD.getCode(),ResultStatusEnum.NOT_PARAM_USER_OR_ERROR_PWD.getMsg());
    }

    /**
     * SecurityUser及角色初始化
     * @param s //登陆账号
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        TSysUser tSysUser = getUser(s);
        if (tSysUser == null) {
            log.error("user login error : userId not find ! 没有发现用户登录账号！");
            throw new UsernameNotFoundException("UserName " + s + " not found");
        }
        List<TSysRole> list = dbUtil.getAllBeanList(SqlEnum.QUERY_SQL_ALL_USER_ROLE_BYUSERID, TSysRole.class, s);
        Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        if (null != list && list.size() > 0) {
            for (TSysRole sysRole : list) {
                authorities.add(new SimpleGrantedAuthority("ROLE_" + sysRole.getName().toUpperCase()));
            }
        }
        return new SecurityUser(tSysUser, authorities);
    }
}
