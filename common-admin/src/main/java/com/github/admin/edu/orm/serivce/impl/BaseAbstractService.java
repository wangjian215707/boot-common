package com.github.admin.edu.orm.serivce.impl;

import com.github.admin.edu.assembly.common.entity.JsonTableCols;
import com.github.admin.edu.orm.entity.OrderEntity;
import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.admin.edu.assembly.common.entity.JsonArray;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.page.entity.Pager;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.entity.DeleteMessage;
import com.github.admin.edu.orm.serivce.TSequenceService;
import com.github.edu.client.common.entity.TBsdtTableCols;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/9
 * Time: 23:38
 */
public abstract class BaseAbstractService<T,ID extends Serializable>{

    @Autowired
    public CustomRepository<T,ID> customRepository;

    @Autowired
    private TSequenceService sequenceService;

    public Long getId(String obj){
        return sequenceService.getUserId(obj);
    }

    /**
     * 分页条件查询
     * @param t
     * @param pager
     * @return
     */
    @Transactional(readOnly = true)
    public JsonArray<T> getAllByPage(T t, Pager pager){
        Page<T> page=customRepository.findByAuto(t,pager);
        if(null!=page){
            pager.setTotalElements(page.getTotalElements());
            pager.setTotalPages(page.getTotalPages());
            List<T> list=page.getContent();
            JsonArray<T> jsonArray=new JsonArray<>();
            jsonArray.setDataList(list);
            jsonArray.setPager(pager);
            return jsonArray;
        }
        return new JsonArray<T>("没有查询到对象！");
    }

    @Transactional(readOnly = true)
    public Map<String,Object> getAllMapByPage(T t,Integer current, Integer rows){
        return getAllMapByPage(t,current,rows,null);
    }

    @Transactional(readOnly = true)
    public Map<String,Object> getAllMapByPage(T t,Integer current, Integer rows,String orders){
        Map<String,Object> map=new HashMap<>();
        Pager pager=new Pager();
        if(null==current){
            current=1;
        }
        if(null==rows){
            rows=10;
        }
        pager.setRows(rows);
        pager.setCurrent(current);
        Page<T> page=null;
        if(StringUtils.isNotBlank(orders)){
            String[] order=orders.split(";");
            List<OrderEntity> list=new ArrayList<>();
            for (String str:order){
                String[] strs=str.split("-");
                if(strs.length>1){
                    OrderEntity orderEntity=new OrderEntity();
                    orderEntity.setKey(strs[0]);
                    Integer val=Integer.parseInt(strs[1]);
                    orderEntity.setType(val);
                    list.add(orderEntity);
                }
            }
            page=customRepository.findByAuto(t,current,rows,list);
        }else {
            page=customRepository.findByAuto(t,pager);
        }
        if(null!=page){
            pager.setTotalElements(page.getTotalElements());
            pager.setTotalPages(page.getTotalPages());
            List<T> list=page.getContent();
            map.put("pager",pager);
            map.put("dataList",list);
        }
        return map;
    }

    @Transactional(readOnly = true)
    public T getEntity(ID var1){
        Optional<T> optional=customRepository.findById(var1);
        if(optional.isPresent()){
            return optional.get();
        }
        return null;
    }

    /**
     * 批量删除
     * @param ids
     * @param type
     * @return
     */
    @Transactional
    public String delete(String ids,String type){
        JsonEntity<DeleteMessage> jsonEntity=new JsonEntity<>();
        DeleteMessage deleteMessage=new DeleteMessage();
        deleteMessage.setNum(0);
        if(StringUtils.isNotBlank(ids)){
            int i=customRepository.deleteByIds(StringUtils.getIterable(type,ids));
            deleteMessage.setNum(i);
        }
        return JsonUtils.toJson(jsonEntity);
    }

    public String ajaxObjNoButton(String... objs) {
        JsonTableCols jsonTableCols=getObjByList(objs);
        return JsonUtils.toJson(jsonTableCols);
    }

    public String ajaxObjByButton(String... objs) {
        JsonTableCols jsonTableCols=getObjByList(objs);
        List<TBsdtTableCols> list=jsonTableCols.getData();
        jsonTableCols.setData(list,1);
        return JsonUtils.toJson(jsonTableCols);
    }

    public String ajaxObjByMaxButton(String ...objs){
        JsonTableCols jsonTableCols=getObjByList(objs);
        List<TBsdtTableCols> list=jsonTableCols.getData();
        jsonTableCols.setData(list,2);
        return JsonUtils.toJson(jsonTableCols);
    }

    public String ajaxRadioObjNoButton(String ...objs){
        JsonTableCols jsonTableCols=getRadioByList(objs);
        return JsonUtils.toJson(jsonTableCols);
    }

    public String ajaxRadioObjByButton(String ...objs){
        JsonTableCols jsonTableCols=getRadioByList(objs);
        List<TBsdtTableCols> list=jsonTableCols.getData();
        jsonTableCols.setData(list,1);
        return JsonUtils.toJson(jsonTableCols);
    }

    private JsonTableCols getRadioByList(String ...objs){
        JsonTableCols jsonTableCols=new JsonTableCols();
        List<TBsdtTableCols> list=jsonTableCols.getData();
        if(null!=list&&list.size()>0){
            TBsdtTableCols tBsdtTableCols=list.get(0);
            tBsdtTableCols.setType("radio");
        }
        if (null != objs && objs.length > 0) {
            for (String str : objs) {
                TBsdtTableCols tBsdtTableCols = new TBsdtTableCols();
                String[] values = str.split(":");
                tBsdtTableCols.setField(values[0]);
                tBsdtTableCols.setTitle(values[1]);
                list.add(tBsdtTableCols);
            }
        }
        jsonTableCols.setData(list);
        return jsonTableCols;
    }

    /**
     * 设置表格对象头信息
     * @param objs
     * @return
     */
    private JsonTableCols getObjByList(String... objs) {
        JsonTableCols jsonTableCols = new JsonTableCols();
        List<TBsdtTableCols> list = jsonTableCols.getData();
        if (null != objs && objs.length > 0) {
            for (String str : objs) {
                TBsdtTableCols tBsdtTableCols = new TBsdtTableCols();
                String[] values = str.split(":");
                tBsdtTableCols.setField(values[0]);
                tBsdtTableCols.setTitle(values[1]);
                list.add(tBsdtTableCols);
            }
        }
        jsonTableCols.setData(list);
        return jsonTableCols;
    }
}
