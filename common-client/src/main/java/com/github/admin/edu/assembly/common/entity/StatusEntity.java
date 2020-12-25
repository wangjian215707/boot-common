package com.github.admin.edu.assembly.common.entity;

import lombok.Data;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-3-25
 */
@Data
public class StatusEntity {

    private Long code;

    private String message;

    public StatusEntity(){
        this.code=200L;
        this.message="操作成功！";
    }

    public StatusEntity(Long code,String message){
        this.code=code;
        this.message=message;
    }


}
