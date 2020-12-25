package com.github.edu.boot2.admin.rest.exception.entity;

import com.github.edu.boot2.admin.util.ResultStatusEnum;
import lombok.Data;

/**
 * Restful 统一返回数据模型
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
@Data
public class Result <T> {
    /**
     * 返回的代码，200表示成功，其他表示失败
     */
    private int code;
    /**
     * 成功或失败时返回的错误信息
     */
    private String msg;
    /**
     * 成功时返回的数据信息
     */
    private T data;

    /**
     * 推荐使用此种方法返回
     * @param resultStatusCode 枚举信息
     * @param data 返回数据
     */
    public Result(ResultStatusEnum resultStatusCode, T data){
        this(resultStatusCode.getCode(), resultStatusCode.getMsg(), data);
    }

    public Result(int code, String msg, T data){
       this.code=code;
       this.msg=msg;
       this.data=data;
    }

    public Result(int code, String msg){
        this(code, msg, null);
    }

    public Result(ResultStatusEnum resultStatusCode){
        this(resultStatusCode, null);
    }

    public Result(T data){
        this.code=ResultStatusEnum.OK.getCode();
        this.msg=ResultStatusEnum.OK.getMsg();
        this.data=data;
    }
}
