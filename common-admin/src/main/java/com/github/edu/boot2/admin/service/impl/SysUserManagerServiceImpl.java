package com.github.edu.boot2.admin.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.common.DBUtil;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysUserDao;
import com.github.edu.boot2.admin.entity.TSysUser;
import com.github.edu.boot2.admin.rest.exception.CustomException;
import com.github.edu.boot2.admin.service.ISysUserManagerService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import com.github.edu.boot2.admin.util.ResultStatusEnum;
import com.github.edu.boot2.admin.util.SqlEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/27
 */
@Slf4j
@Service
public class SysUserManagerServiceImpl extends BaseAbstractService<TSysUser,Long>implements ISysUserManagerService {

    @Autowired
    private ISysUserDao dao;

    @Autowired
    private DBUtil dbUtil;

    @Override
    public TSysUser saveOrUpdate(TSysUser tSysUser) {
        if(null==tSysUser){
            throw new CustomException(ResultStatusEnum.BAD_REQUEST.getCode(),ResultStatusEnum.BAD_REQUEST.getMsg());
        }
        return dao.save(tSysUser);
    }

    @Override
    public String delete(String ids) {
        return null;
    }

    /**
     * 查询用户基本信息
     *
     * @param userId
     * @return
     */
    @Override
    public TSysUser getUserInformation(String userId) {
        if(StringUtils.isBlank(userId)){
            throw new CustomException(ResultStatusEnum.BAD_REQUEST.getCode(),ResultStatusEnum.BAD_REQUEST.getMsg());
        }
        List<TSysUser> list=dbUtil.getAllBeanList(SqlEnum.QUERY_SQL_ENTITY_USER_LOGIN,TSysUser.class,userId);
        if(null!=list&&list.size()>0){
            return list.get(0);
        }
        return null;
    }

    /**
     * 保存用户基本信息
     *
     * @param code
     * @return
     */
    @Override
    public TSysUser saveOrUpdateObject(String code) {
        if(StringUtils.isBlank(code)){
            throw new CustomException(ResultStatusEnum.BAD_REQUEST.getCode(),ResultStatusEnum.BAD_REQUEST.getMsg());
        }
        TSysUser tSysUser= JsonUtils.toCollection(code, new TypeReference<TSysUser>() {});
        return saveOrUpdate(tSysUser);
    }
}
