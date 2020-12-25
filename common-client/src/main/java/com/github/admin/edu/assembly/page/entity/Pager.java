package com.github.admin.edu.assembly.page.entity;

import java.io.Serializable;

/**
 *
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-11
 */
public class Pager implements Serializable {

    private Integer current;//第几页

    private Integer rows;//每页几条

    private Integer totalPages;//一共几页

    private Long totalElements;//一共多少条数据

    private String[] order;//排序字段

    private String direction;//排序方式

    public Pager(){
        this.direction="DESC";
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        this.current = current;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public String[] getOrder() {
        return order;
    }

    public void setOrder(String[] order) {
        this.order = order;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }
}
