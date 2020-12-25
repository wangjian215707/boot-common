package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.assembly.date.util.DateFormatUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysUserTokenDao;
import com.github.edu.boot2.admin.entity.TSysUserToken;
import com.github.edu.boot2.admin.service.ISysUserTokenService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * token 相关操作
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/21
 */
@Slf4j
@Service
public class SysUserTokenServiceImpl extends BaseAbstractService<TSysUserToken,Long> implements ISysUserTokenService {

    @Autowired
    private ISysUserTokenDao dao;

    @Override
    public TSysUserToken getUserToken(String cid, String uid) {
        if(StringUtils.isNotBlank(cid)&&StringUtils.isNotBlank(uid)){
            try {
                String sysTime= DateFormatUtils.formatDate(new Date(),"yyyyMMddHHmmss");
                List<TSysUserToken> list=dao.queryAllByClientIdAndAndUserId(cid,uid,Long.parseLong(sysTime));
                if(null!=list&&list.size()>0){
                    return list.get(0);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public TSysUserToken saveOrUpdate(TSysUserToken tSysUserToken) {
        return dao.save(tSysUserToken);
    }

    @Override
    public String delete(String ids) {
        return super.delete(ids, ConstantEnum.ENUM_ID_TYPE_lONG.getCode());
    }
}
