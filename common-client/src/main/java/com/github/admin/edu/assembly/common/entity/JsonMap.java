package com.github.admin.edu.assembly.common.entity;

import com.github.admin.edu.assembly.page.entity.Pager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * jsonMap对象
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-4-16
 */
public class JsonMap {

    /**
     * dataList类型
     * @param list
     * @param <T>
     * @return
     */
    public static <T>Map<String,Object> getTrueMap(List<T> list){
        Map<String,Object> map=new HashMap<>();
        map.put("state",200);
        map.put("msg","ok");
        map.put("dataList",list);
        return map;
    }

    /**
     * data类型数据
     * @param t
     * @param <T>
     * @return
     */
    public static <T>Map<String,Object> getTrueEntityMap(T t){
        Map<String,Object> map=new HashMap<>();
        map.put("state",200);
        map.put("msg","ok");
        map.put("data",t);
        return map;
    }

    /**
     * data类型数据
     * @param t
     * @param <T>
     * @return
     */
    public static <T>Map<String,Object> getTrueEntityMap(T t, Pager pager){
        Map<String,Object> map=new HashMap<>();
        map.put("state",200);
        map.put("msg","ok");
        map.put("data",t);
        map.put("pager",pager);
        return map;
    }

    /**
     * 正确返回带分页的结果数据
     * @param list
     * @param pager
     * @param <T>
     * @return
     */
    public static <T>Map<String,Object> getTrueMap(List<T> list,Pager pager){
        Map<String,Object> map=getTrueMap(list);
        map.put("pager",pager);
        return map;
    }

    /**
     * 返回单个数值
     * @param value
     * @return
     */
    public static Map<String,Object> getTrueMap(Object value){
        Map<String,Object> map=new HashMap<>();
        map.put("state",200);
        map.put("msg","ok");
        map.put("value",value);
        return map;
    }

    /**
     * 当数据异常的时候，返回
     * @param msg
     * @return
     */
    public static Map<String,Object> getErrorMap(String msg){
        Map<String,Object> map=new HashMap<>();
        map.put("state", 3001);
        map.put("msg",msg);
        map.put("dataList",null);
        return map;
    }

}
