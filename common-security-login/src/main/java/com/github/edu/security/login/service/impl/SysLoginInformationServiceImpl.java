package com.github.edu.security.login.service.impl;

import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.security.login.domain.ISysUserLoginDao;
import com.github.edu.security.login.entity.TSysLogin;
import com.github.edu.security.login.service.ISysLoginInformationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 记录用户登陆信息相关日志
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/18
 */
@Slf4j
@Service
public class SysLoginInformationServiceImpl extends BaseAbstractService<TSysLogin,Long> implements ISysLoginInformationService {

    @Autowired
    private ISysUserLoginDao dao;

    @Override
    public TSysLogin saveOrUpdate(TSysLogin tSysLogin) {
        return null;
    }

    @Override
    public String delete(String ids) {
        return null;
    }

    @Override
    public Map<String, Object> getAllMapByPage(TSysLogin tSysLogin, Integer current, Integer rows) {
        return null;
    }

    @Override
    public Map<String, Object> getAllMapByPage(TSysLogin tSysLogin, Integer current, Integer rows, String orders) {
        return null;
    }
}
