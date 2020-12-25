package com.github.edu.client.common.constant;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-4-3
 */
public class StringHelper {
    public static String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }
    public static int getObjectIntegerValue(Object obj){
        return  obj==null?0:obj.hashCode();
    }
}
