package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysSystemDao;
import com.github.edu.boot2.admin.entity.TSysSystem;
import com.github.edu.boot2.admin.service.ISysSystemService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 * 接入系统管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/16
 */
@Service
@Slf4j
public class SysSystemServiceImpl extends BaseAbstractService<TSysSystem,Long> implements ISysSystemService {

    @Autowired
    private ISysSystemDao dao;

    @Override
    public TSysSystem saveOrUpdate(TSysSystem tSysSystem) {
        return dao.save(tSysSystem);
    }

    @Override
    public String delete(String ids) {
        return super.delete(ids,ConstantEnum.ENUM_ID_TYPE_lONG.getCode());
    }

    /**
     * 根据编码查询接入系统信息
     * @param code
     * @return
     */
    @Override
    public TSysSystem getTSysStem(String code,Integer yzfs) {
        if(StringUtils.isNotBlank(code)){
            List<TSysSystem> list=dao.queryByCode(code, ConstantEnum.ENUM_STATE_QY.getNum(),yzfs);
            if(null!=list&&list.size()>0){
                return list.get(0);
            }
        }
        return null;
    }
}
