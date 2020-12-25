package com.github.edu.boot2.admin.service.impl;

import com.github.admin.edu.orm.common.DBUtil;
import com.github.admin.edu.orm.serivce.impl.BaseAbstractService;
import com.github.edu.boot2.admin.dao.ISysMenuDao;
import com.github.edu.boot2.admin.entity.TSysMenu;
import com.github.edu.boot2.admin.service.ISysMenuService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统菜单管理，相关操作及接口处理类
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/20
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends BaseAbstractService<TSysMenu,Long> implements ISysMenuService {

    @Autowired
    private DBUtil dbUtil;

    @Autowired
    private ISysMenuDao dao;




    /**
     * 权限校验使用
     *
     * @return
     */
    @Cacheable(value = "sys_menu")
    @Override
    public List<TSysMenu> getAllMenuToSecurityDataSource() {
        return dao.queryAllByZtAndIsRemove(ConstantEnum.ENUM_STATE_QY.getNum(),ConstantEnum.ENUM_STATE_NO.getNum());
    }

    /**
     * 清楚全部缓存
     * @return
     */
    @CacheEvict(value = "sys_menu",allEntries = true)
    public List<TSysMenu> getAllMenuToSecurityDataSourceNow(){
        return dao.queryAllByZtAndIsRemove(ConstantEnum.ENUM_STATE_QY.getNum(),ConstantEnum.ENUM_STATE_NO.getNum());
    }

    @Override
    public TSysMenu saveOrUpdate(TSysMenu tSysMenu) {
        return dao.save(tSysMenu);
    }

    @Override
    public String delete(String ids) {
        return super.delete(ids,"Long");
    }
}
