package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysRoleDao;
import com.github.edu.boot2.admin.entity.TSysRole;
import com.github.edu.boot2.admin.rest.exception.CustomException;
import com.github.edu.boot2.admin.service.ISysRoleService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import com.github.edu.boot2.admin.util.ResultStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/22
 */
@Slf4j
@Service
public class SysRoleServiceImpl extends BaseAbstractService<TSysRole,Long> implements ISysRoleService {

    @Autowired
    private ISysRoleDao dao;

    @Override
    public List<TSysRole> queryAllByMenuId(Long menuId) {
        if(null==menuId){
           throw new CustomException(ResultStatusEnum.BAD_REQUEST.getCode(),ResultStatusEnum.BAD_REQUEST.getMsg());
        }
        return dao.queryAllByIdForFunction(menuId, ConstantEnum.ENUM_STATE_QY.getNum());
    }

    @Override
    public List<TSysRole> queryAllByFunctionId(Long functionId) {
        if(null==functionId){
            throw new CustomException(ResultStatusEnum.BAD_REQUEST.getCode(),ResultStatusEnum.BAD_REQUEST.getMsg());
        }
        return dao.queryAllByIdForApi(functionId,ConstantEnum.ENUM_STATE_QY.getNum());
    }

    /**
     * 根据用户登陆账号，查询用户具有的角色
     *
     * @param userId
     * @return
     */
    @Override
    public List<TSysRole> queryAllByUserId(String userId) {
        if(StringUtils.isBlank(userId)){
            throw new CustomException(ResultStatusEnum.BAD_REQUEST.getCode(),ResultStatusEnum.BAD_REQUEST.getMsg());
        }
        return dao.queryAllByUserId(ConstantEnum.ENUM_STATE_QY.getNum(),userId);
    }

    @Override
    public TSysRole saveOrUpdate(TSysRole tSysRole) {
        return dao.save(tSysRole);
    }

    @Override
    public String delete(String ids) {
        return super.delete(ids,"Long");
    }
}
