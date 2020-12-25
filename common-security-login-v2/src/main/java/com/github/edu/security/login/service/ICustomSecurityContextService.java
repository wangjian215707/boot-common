package com.github.edu.security.login.service;

import org.springframework.security.access.ConfigAttribute;

import java.util.Collection;
import java.util.Map;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/8/29
 */
public interface ICustomSecurityContextService {


    Map<String,Collection<ConfigAttribute>> getCollectionMap();

    Collection<ConfigAttribute> getCollection(String path);
}
