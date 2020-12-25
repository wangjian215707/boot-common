package com.github.admin.edu.assembly.common.entity;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-8-28
 */
public class LayerUploadMsg {

    public LayerUploadMsg(){
        this.code=0;
        this.msg="";
        this.error=0;
    }

    private Map<String,String> data=new HashMap<>();

    public Map<String, String> getData() {
        return data;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    private int code;//错误码

    private String msg;//错误描述

    private String url;//地址

    private int error;//错误码

    private String oldFileName;//历史文件名称

    private String newFileName;//保存后文件名称

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getOldFileName() {
        return oldFileName;
    }

    public void setOldFileName(String oldFileName) {
        this.oldFileName = oldFileName;
    }

    public String getNewFileName() {
        return newFileName;
    }

    public void setNewFileName(String newFileName) {
        this.newFileName = newFileName;
    }
}
