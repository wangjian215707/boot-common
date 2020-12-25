package com.github.admin.edu.assembly.common.entity;

import com.github.admin.edu.assembly.page.entity.Pager;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-4-3
 */
public class JsonArray<T> {

    private Integer state;

    private String msg;

    private Pager pager;

    private String userId;

    private List<T> dataList;
    public JsonArray(){
        this.state=200;
        this.msg="ok";
    }
    public JsonArray(String msg){
        this.msg=msg;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }

    public Pager getPager() {
        return pager;
    }

    public void setPager(Pager pager) {
        this.pager = pager;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
