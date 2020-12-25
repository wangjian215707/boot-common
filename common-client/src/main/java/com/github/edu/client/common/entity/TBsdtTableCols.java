package com.github.edu.client.common.entity;


import lombok.Data;

/**
 * 数据表格头设置
 */
@Data
public class TBsdtTableCols {

    private String field;//字段名称

    private String title;//显示名称

    private Object width;//宽度

    private Integer minWidth;//最小宽度

    private String type ="normal";//normal常规列；checkbox复选框；radio单选框；numbers序号；space空列

    private Boolean LAY_CHECKED=false;//是否全选

    private String fixed;//固定列

    private Boolean hide=false;//是否初始隐藏列

    private Boolean totalRow=false;//合计功能

    private String totalRowText;//合计模版 如：合计:

    private Boolean sort=true;//是否设置排序

    private Boolean unresize=false;//是否禁止拖动

    private String edit;

    private String event;

    private String style;

    private String align="left";

    private Integer colspan=1;

    private Integer rowspan=1;

    private String templet;

    private String toolbar;


}
