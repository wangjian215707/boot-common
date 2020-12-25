package com.github.admin.edu.assembly.common.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class TMenuEntity {

    private Object id;//主键

    private Object pid;

    private String title;//标题

    private String icon;//图标

    private List<TMenuEntity> list=new ArrayList<>();//子节点

    private String url;//地址
}
