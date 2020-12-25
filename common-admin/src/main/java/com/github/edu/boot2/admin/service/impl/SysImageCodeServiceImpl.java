package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysImageCodeDao;
import com.github.edu.boot2.admin.entity.TSysImageCode;
import com.github.edu.boot2.admin.service.ISysImageCodeService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
@Slf4j
@Service
public class SysImageCodeServiceImpl extends BaseAbstractService<TSysImageCode,Long> implements ISysImageCodeService {

    @Autowired
    private ISysImageCodeDao service;

    @Override
    public TSysImageCode queryByUUID(String uuid) {
        if(StringUtils.isNotBlank(uuid)){
            List<TSysImageCode> list=service.getAllByUuid(uuid);
            if(null!=list&&list.size()>0){
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public TSysImageCode saveOrUpdate(TSysImageCode tSysImageCode) {
        return service.save(tSysImageCode);
    }

    @Override
    public String delete(String ids) {
        return super.delete(ids, ConstantEnum.ENUM_ID_TYPE_lONG.getCode());
    }
}
