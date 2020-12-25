package com.github.admin.edu.assembly.common.entity;

import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-8-2
 */
public class JsonTree<T> {

    private Integer code;//状态码

    private String msg;//返回消息

    private Long count;//条目数量

    private List<T> data;//数据对象

    private StatusEntity status;

    public JsonTree(){
        this.code=0;
        this.msg="ok";
        this.status=new StatusEntity();
    }

    public JsonTree(String msg){
        this.code=3001;
        this.msg=msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public void setStatus(StatusEntity status) {
        this.status = status;
    }
}
