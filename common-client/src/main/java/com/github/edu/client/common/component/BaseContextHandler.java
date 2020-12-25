package com.github.edu.client.common.component;

import com.github.edu.client.common.constant.CommonConstants;
import com.github.edu.client.common.constant.StringHelper;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseContextHandler {

    public static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>();

    public static void set(String key, Object value) {
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        map.put(key, value);
    }

    public static Object get(String key){
        Map<String, Object> map = threadLocal.get();
        if (map == null) {
            map = new HashMap<String, Object>();
            threadLocal.set(map);
        }
        return map.get(key);
    }

    public static String getTree(){
        Object value=get(CommonConstants.MENU_TREE);
        return returnObjectValue(value);
    }

    public static void setTreeMenu(String value){
        set(CommonConstants.MENU_TREE,value);
    }

    public static String getUserID(){
        Object value = get(CommonConstants.CONTEXT_KEY_USER_ID);
        return returnObjectValue(value);
    }

    public static String getUsername(){
        Object value = get(CommonConstants.CONTEXT_KEY_USERNAME);
        return returnObjectValue(value);
    }

    public static void setWxOpenId(String openId){
        set(CommonConstants.WX_OPENID,openId);
    }

    public static String getOpenId(){
        Object value=get(CommonConstants.WX_OPENID);
        return returnObjectValue(value);
    }

    public static void setWxImageUrl(String url){
        set(CommonConstants.WX_IMAGE,url);
    }

    public static String getWxImage(){
        Object value=get(CommonConstants.WX_IMAGE);
        return returnObjectValue(value);
    }

    public static String getUserType(){
        Object value = get(CommonConstants.CONTEXT_KEY_USER_TYPE);
        return returnObjectValue(value);
    }

    public static void setUserType(String userType){
        set(CommonConstants.CONTEXT_KEY_USER_TYPE,userType);
    }
    public static String getName(){
        Object value = get(CommonConstants.CONTEXT_KEY_USER_NAME);
        return StringHelper.getObjectValue(value);
    }

    public static String getToken(){
        Object value = get(CommonConstants.CONTEXT_KEY_USER_TOKEN);
        return StringHelper.getObjectValue(value);
    }
    public static void setToken(String token){set(CommonConstants.CONTEXT_KEY_USER_TOKEN,token);}

    public static void setName(String name){set(CommonConstants.CONTEXT_KEY_USER_NAME,name);}

    public static void setUserID(String userID){
        set(CommonConstants.CONTEXT_KEY_USER_ID,userID);
    }

    public static void setUsername(String username){
        set(CommonConstants.CONTEXT_KEY_USERNAME,username);
    }

    private static String returnObjectValue(Object value) {
        return value==null?null:value.toString();
    }

    public static void remove(){
        threadLocal.remove();
    }
}
