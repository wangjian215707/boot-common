package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.entity.DeleteMessage;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysDeptManagerDao;
import com.github.edu.boot2.admin.entity.TSysOrganization;
import com.github.edu.boot2.admin.service.ISysDeptManagerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统部门管理
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/6/9
 */
@Slf4j
@Service
public class SysDeptManagerServiceImpl extends BaseAbstractService<TSysOrganization,Long> implements ISysDeptManagerService {

    @Autowired
    private ISysDeptManagerDao deptManagerDao;

    @Override
    public TSysOrganization saveOrUpdate(TSysOrganization tSysOrganization) {
        return deptManagerDao.save(tSysOrganization);
    }

    @Override
    public String delete(String ids) {
        JsonEntity<DeleteMessage> jsonEntity=new JsonEntity<>();
        DeleteMessage deleteMessage=new DeleteMessage();
        deleteMessage.setNum(0);
        if(StringUtils.isNotBlank(ids)){
           List<String> val= StringUtils.getIterable("String",ids);
           if(null!=val&&val.size()>0){
               for (String str:val){
                   int i=deptManagerDao.deleteAllById(str,"str%");
                   deleteMessage.setNum(deleteMessage.getNum()+i);
               }
           }
        }
        return JsonUtils.toJson(jsonEntity);
    }
}
