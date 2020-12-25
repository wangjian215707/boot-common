package com.github.admin.edu.orm.serivce.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.github.admin.edu.orm.repository.CustomRepository;
import com.github.admin.edu.orm.serivce.TSequenceService;
import com.github.admin.edu.assembly.common.entity.JsonArray;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.common.util.ObjectUtil;
import com.github.admin.edu.assembly.page.entity.Pager;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.entity.DeleteMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-11
 */
public abstract class DataRestfulAbstractBaseService<T,ID extends Serializable> {

    private static Logger log=LoggerFactory.getLogger(DataRestfulAbstractBaseService.class);

    @Autowired
    private CustomRepository<T,ID> customRepository;

    @Autowired
    public TSequenceService sequenceService;

    /**
     * 分页条件查询
     * @param t
     * @param pager
     * @return
     */
    @Transactional(readOnly = true)
    public String getAllByPage(T t, Pager pager){
        Page<T> page=customRepository.findByAuto(t,pager);
        if(null!=page){
            pager.setTotalElements(page.getTotalElements());
            pager.setTotalPages(page.getTotalPages());
            List<T> list=page.getContent();
            JsonArray<T> jsonArray=new JsonArray<>();
            jsonArray.setDataList(list);
            jsonArray.setPager(pager);
            return JsonUtils.toJson(jsonArray);
        }
        return JsonUtils.toJson(new JsonArray<T>("没有查询到对象！"));
    }
    @Transactional(readOnly = true)
    public String getALLPostByPager(String jsonEntity){
        if(StringUtils.isNotBlank(jsonEntity)){
            JsonEntity<T> tJsonEntity=JsonUtils.toCollection(jsonEntity, new TypeReference<JsonEntity<T>>() {});
            if(null!=tJsonEntity){
                T t=tJsonEntity.getData();
                if(t.hashCode()==0){
                    return getAllByPage(null,tJsonEntity.getPager());
                }
                log.info("------------"+t);
                return getAllByPage(t,tJsonEntity.getPager());
            }
        }
        return JsonUtils.toJson(new JsonArray<T>("没有查询到对象！"));
    }

    @Transactional(readOnly = true)
    public String getALLPostByPager(T t,Pager pager){
        return getAllByPage(t,pager);
    }
    @Transactional(readOnly = true)
    public String getAllGetEntity(T t,Integer current, Integer rows, String order, String fileId){
        t=setObjectValue(t,fileId);
        Pager pager=new Pager();
        if(null==current||0==current){
            current=1;
        }
        if(null==rows||0==rows){
            rows=1000;
        }
        pager.setCurrent(current);
        pager.setRows(rows);
        if(StringUtils.isNotBlank(order)){
            String[] orders=order.split(":");
            if(null!=orders&&orders.length>0){
                String cloum=orders[0];
                String dec=orders[1];
                if(StringUtils.isNotBlank(cloum)){
                    String[] cloums=cloum.split(",");
                    pager.setOrder(cloums);
                }
                if(StringUtils.isNotBlank(dec)){
                    pager.setDirection(dec);
                }
            }
        }
        return getAllByPage(t,pager);
    }

    /**
     * 查询单个对象
     * @param id
     * @return
     */
    @Transactional(readOnly = true)
    public String getEntity(ID id){
        if(null!=id){
            JsonEntity jsonEntity=new JsonEntity<>();
            Optional<T> optional=customRepository.findById(id);
            if(optional.isPresent()){
                T t=customRepository.findById(id).get();
                if(null!=t){
                    jsonEntity.setData(t);
                }
                return JsonUtils.toJson(jsonEntity);
            }
            return JsonUtils.toJson(new JsonEntity<T>("没有查询到对象"));
        }
        return JsonUtils.toJson(new JsonEntity<T>("没有ID值"));
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

    public T setObjectValue(T t,String fileId){

        if(StringUtils.isNotBlank(fileId)){
            String[] file=fileId.split(";");
            for (String str:file){
                String[] keys=str.split(":");
                ObjectUtil.setValue(t,keys[0],keys[1]);
            }
        }
        return t;
    }
    public Long getId(String obj){
        return sequenceService.getUserId(obj);
    }
}
