package com.github.admin.edu.assembly.common.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-3-25
 */
@Data
public class TreeEntity {

    private String id;//主键

    private String title;//树名称

    private List<CheckArr> checkArr =new ArrayList<>();//复选框使用

    private String parentId; //父节点编码

    private String iconClass;//节点图标样式

    private List<TreeEntity> children=new ArrayList<>();//子节点

    /** 是否展开节点*/
    private Boolean spread;
    /** 是否最后一级节点*/
    private Boolean isLast;

    /** 表示用户自定义需要存储在树节点中的数据*/
    private Object basicData;

    private String selected;//是否选中



}
