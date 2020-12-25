package com.github.admin.edu.assembly.common.entity;

import com.github.admin.edu.assembly.page.entity.Pager;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-4-3
 */
public class JsonEntity<T>{

    private Integer state;

    private String msg;
   /* plp['p[/']*/
    private T data;

    private Pager pager;

    private String userId;

    public <T>JsonEntity(){
        this.state=200;
        this.msg="ok";
    }

    public <T> JsonEntity(String msg){
        this.state= 3001;
        this.msg=msg;
    }

    public <T>JsonEntity(Integer state,String msg){
        this.state=state;
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

    public T getData() {
        return data;
    }

    public void
    setData(T data) {
        this.data = data;
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
