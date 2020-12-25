package com.github.admin.edu.orm.entity;

import com.github.edu.client.common.util.SortEnum;
import lombok.Data;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/3/2
 */
@Data
public class OrderEntity {

    private String key;

    private int type = SortEnum.DESC.getCode();//排序方式 0 ：desc;1:asc
}
