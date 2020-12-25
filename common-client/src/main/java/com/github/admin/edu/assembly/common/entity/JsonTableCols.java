package com.github.admin.edu.assembly.common.entity;

import com.github.edu.client.common.entity.TBsdtTableCols;
import java.util.ArrayList;
import java.util.List;

public class JsonTableCols {

    private List<TBsdtTableCols> data;

    public List<TBsdtTableCols> getData() {
        return data;
    }

    public void setData(List<TBsdtTableCols> data) {
        this.data = data;
    }

    public void setData(List<TBsdtTableCols> data,Integer type){
        if(null!=data){
            TBsdtTableCols tBsdtTableCols=new TBsdtTableCols();
            tBsdtTableCols.setToolbar("#toolsButton");
            tBsdtTableCols.setFixed("right");
            tBsdtTableCols.setAlign("center");
            tBsdtTableCols.setSort(false);
            tBsdtTableCols.setTitle("操作");

            if(1==type){
                tBsdtTableCols.setMinWidth(200);
            }else if(2==type){
                tBsdtTableCols.setMinWidth(250);
            }
            data.add(tBsdtTableCols);
        }
        setData(data);
    }

    /**
     * 默认开启复选框，并且固定在左边
     */
    public JsonTableCols(){
        List<TBsdtTableCols> list=new ArrayList<>();
        TBsdtTableCols tBsdtTableCols=new TBsdtTableCols();
        tBsdtTableCols.setType("checkbox");
        tBsdtTableCols.setFixed("left");
        list.add(tBsdtTableCols);
        this.data=list;
    }

    public JsonTableCols(List<TBsdtTableCols> data){
        this.data=data;
    }


}
