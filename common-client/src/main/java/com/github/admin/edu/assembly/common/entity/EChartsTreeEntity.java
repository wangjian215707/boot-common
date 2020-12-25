package com.github.admin.edu.assembly.common.entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * EChartsTree 对象
 */
@Data
public class EChartsTreeEntity {

    private String name ;

    private Object id;

    private Object pid;

    private List<EChartsTreeEntity> children=new ArrayList<>();//子节点

    private Object value;
}
