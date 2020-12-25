package com.github.edu.boot2.admin.service.impl;

import com.github.edu.boot2.admin.dao.ISysFunctionDao;
import com.github.edu.boot2.admin.entity.TSysFunction;
import com.github.edu.boot2.admin.service.ISysFunctionService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/22
 */
@Service
@Slf4j
public class SysFunctionServiceImpl implements ISysFunctionService {

    @Autowired
    private ISysFunctionDao functionDao;

    /**
     * 查询全部接口信息
     *
     * @return
     */
    @Cacheable(value = "sys_api")
    @Override
    public List<TSysFunction> queryAllApi() {
        return functionDao.queryAllByApi(ConstantEnum.ENUM_STATE_NO.getNum(),
                ConstantEnum.ENUM_STATE_QY.getNum(),
                ConstantEnum.FUNCTION_TYPE_API.getNum());
    }

    /**
     * 清楚全部缓存
     * @return
     */
    @CacheEvict(value = "sys_api",allEntries = true)
    @Override
    public List<TSysFunction> queryAllApiNow() {
        return functionDao.queryAllByApi(ConstantEnum.ENUM_STATE_NO.getNum(),
                ConstantEnum.ENUM_STATE_QY.getNum(),
                ConstantEnum.FUNCTION_TYPE_API.getNum());
    }
}
