package com.github.admin.edu.assembly.common.util;

import com.github.admin.edu.assembly.common.entity.JsonEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-19
 */
public class ObjectUtil {

    private static final Logger log=LoggerFactory.getLogger(ObjectUtil.class);

    public static List<Object> getListObject(Object obj, List<String> keys){
        List<Object> list=new ArrayList<>();
        if(null!=keys&&keys.size()>0){
            // 得到类对象
            Class userCla = (Class) obj.getClass();
            /* 得到类中的所有属性集合 */
            Field[] fs = userCla.getDeclaredFields();
            if(null!=fs&&fs.length>0){
                for (String key:keys){
                    for (Field field:fs){
                        field.setAccessible(true); // 设置些属性是可以访问的
                        if(field.getName().endsWith(key)){
                            try {
                                list.add(field.get(obj));
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            }
                            break;
                        }
                    }
                }
            }
        }
        return list;
    }

    /**
     * 通过反射获取指定属性值
     * @param obj
     * @param key
     * @return
     */
    public static Object getValueByKey(Object obj, String key) {
        // 得到类对象
        Class userCla = (Class) obj.getClass();
        /* 得到类中的所有属性集合 */
        Field[] fs = userCla.getDeclaredFields();
        for (int i = 0; i < fs.length; i++) {
            Field f = fs[i];
            f.setAccessible(true); // 设置些属性是可以访问的
            try {
                if (f.getName().endsWith(key)) {
                    return f.get(obj);
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        // 没有查到时返回空字符串
        return null;
    }

    public static <T>Object setValue(T t,String key,Object value){
        Field f = null;
        try {
            f = t.getClass().getDeclaredField(key);
            f.setAccessible(true);
            f.set(t, value);
        } catch (NoSuchFieldException e) {
            log.info("error!!"+e.getMessage());
            return JsonUtils.toJson(new JsonEntity<T>("反序列化错误！"));
        } catch (IllegalAccessException e) {
            log.info("error!!"+e.getMessage());
            return JsonUtils.toJson(new JsonEntity<T>("反序列化错误！"));
        }
        return t;
    }
}
