package com.github.edu.security.login.service.impl;

import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.security.login.dao.IPermissionDao;
import com.github.edu.security.login.dao.IRoleDao;
import com.github.edu.security.login.entity.TSysPermission;
import com.github.edu.security.login.entity.TSysRole;
import com.github.edu.security.login.service.ICustomSecurityContextService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * 根据资源查询当前资源所需要的权限
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/8/29
 */
@Slf4j
@Service
public class CustomSecurityContextServiceImpl implements ICustomSecurityContextService {

    private Map<String,Collection<ConfigAttribute>> collectionMap =null;

    /**
     * 角色信息
     */
    @Autowired
    private IRoleDao roleDao;

    @Autowired
    private IPermissionDao permissionDao;

    /**
     * 根据路径获取其对应的角色列表
     *
     * @param path
     * @return
     */
    public Collection<ConfigAttribute> loadMetadataSource(String path) {
        if(StringUtils.isNotBlank(path)){
            List<TSysRole> list=roleDao.getAllByPermissionPath(path);
            if(null!=list&&list.size()>0){
                Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                for (TSysRole tSysRole:list){
                    ConfigAttribute configAttribute=new SecurityConfig(tSysRole.getName());
                    configAttributes.add(configAttribute);
                }
                return configAttributes;
            }
            log.error("没有获取到相关权限！");
            return null;
        }
        log.error("没有获取到资源路径");
        return null;
    }

    /**
     * 获取全部资源并将资源信息保存到collectionMap当中，
     * 后期考虑
     * PostConstruct 启动时执行
     */
    @PostConstruct
    public void loadMetadataSource() {
        TSysPermission tSysPermission=new TSysPermission();
        tSysPermission.setIsEnable(1L);
        tSysPermission.setIsRemove(0L);
        List<TSysPermission> list=permissionDao.findByAuto(tSysPermission);
        collectionMap=new HashMap<>();
        if(null!=list&&list.size()>0){
            for (TSysPermission permission:list){
                Collection<ConfigAttribute> configAttributes=new ArrayList<>();
                configAttributes.add(new SecurityConfig("ROLE_admin"));
                if(null!=permission.getRoles()&&permission.getRoles().size()>0){
                    for (TSysRole role:permission.getRoles()){
                        ConfigAttribute configAttribute=new SecurityConfig("ROLE_"+role.getName());
                        configAttributes.add(configAttribute);
                    }
                }
                collectionMap.put(permission.getPath(),configAttributes);
            }
        }
        log.info(JsonUtils.toJson(collectionMap));
    }

    public Map<String,Collection<ConfigAttribute>> getCollectionMap(){
        if(null==collectionMap||collectionMap.size()==0){
            loadMetadataSource();
        }
        return collectionMap;
    }

    @Override
    public Collection<ConfigAttribute> getCollection(String path) {
        if(StringUtils.isNotBlank(path)){
            if(null!=getCollectionMap()){
                return getCollectionMap().get(path);
            }
        }
        return null;
    }
}
