package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysOnlineUserDao;
import com.github.edu.boot2.admin.entity.TSysOnlineUser;
import com.github.edu.boot2.admin.service.ISysUserOnlineService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/23
 */
@Service
@Slf4j
public class SysUserOnlineServiceImpl extends BaseAbstractService<TSysOnlineUser,Long> implements ISysUserOnlineService {

    @Autowired
    private ISysOnlineUserDao userDao;

    /**
     * 删除非当前登陆用户
     *
     * @param userId
     * @param token
     * @return
     */
    @Override
    public int deleteOnlineUser(String userId, String token) {
        return userDao.deleteAllByUserIdAndToken(userId,token);
    }

    @Override
    public TSysOnlineUser saveOrUpdate(TSysOnlineUser tSysOnlineUser) {
        return userDao.save(tSysOnlineUser);
    }

    @Override
    public String delete(String ids) {
        return super.delete(ids, ConstantEnum.ENUM_ID_TYPE_lONG.getCode());
    }
}
