package com.github.edu.client.common.service;

import com.github.admin.edu.assembly.common.entity.JsonArray;
import com.github.admin.edu.assembly.page.entity.Pager;

import java.util.Map;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-25
 */
public interface BaseService<T,ID> {

    JsonArray<T> getAllByPage(T t, Pager pager);

    T getEntity(ID id);

    T saveOrUpdate(T t);

    String delete(String ids);

    String ajaxObjNoButton(String ...objs);

    String ajaxRadioObjNoButton(String ...objs);

    String ajaxObjByButton(String ...objs);

    String ajaxObjByMaxButton(String ...objs);

    String ajaxRadioObjByButton(String ...objs);

    Map<String,Object> getAllMapByPage(T t,Integer current,Integer rows);

    Map<String,Object> getAllMapByPage(T t,Integer current,Integer rows,String orders);

}
