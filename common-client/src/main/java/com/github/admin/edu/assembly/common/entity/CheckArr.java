package com.github.admin.edu.assembly.common.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * DTree 复选框属性设置
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-3-25
 */
@Data
public class CheckArr implements Serializable {

    private  String type;//复选框标记

    private String isChecked;//是否选中
}
