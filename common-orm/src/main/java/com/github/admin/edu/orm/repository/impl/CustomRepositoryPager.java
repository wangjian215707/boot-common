package com.github.admin.edu.orm.repository.impl;
import com.github.admin.edu.assembly.page.entity.Pager;
import com.github.admin.edu.orm.entity.OrderEntity;
import com.github.edu.client.common.util.SortEnum;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangj on 2017/7/13.
 */
public class CustomRepositoryPager {



    public static Pageable pageable(List<OrderEntity> list){

        return pageable(1,10,list);
    }

    public static Pageable pageable(Integer current,Integer rows,List<OrderEntity> list){
        Pageable pageable=null;
        if(null==current){
            current=1;
        }
        if(null==rows){
            rows=10;
        }
        if(null!=list&&list.size()>0){
            List<Order> orders=new ArrayList<>();
            for (OrderEntity orderEntity:list){
                if(orderEntity.getType()==SortEnum.DESC.getCode()){
                    orders.add(new Order(Sort.Direction.DESC,orderEntity.getKey()));
                }else {
                    orders.add(new Order(Sort.Direction.ASC,orderEntity.getKey()));
                }
            }
            pageable=PageRequest.of(current-1,rows,Sort.by(orders));
        }else {
            pageable=PageRequest.of(current-1,rows, Sort.Direction.DESC,"id");
        }

        return pageable;
    }

    /**
     * 分页设置
     * @param pager
     * @return
     */
    public static Pageable pageable(Pager pager){
        Pageable pageable=null;
        int pageSize=10;
        if(null==pager){
            pageable=PageRequest.of(0,pageSize, Sort.Direction.DESC,"id");
        }else{
            int page=0;
            if(null!=pager.getCurrent()&&pager.getCurrent()>0){
                page=pager.getCurrent()-1;
            }

            if(null!=pager.getRows()&&pager.getRows()>0){
                pageSize=pager.getRows();
            }
            String[] orders={"id"};
            if(!StringUtils.isEmpty(pager.getOrder())){
                orders=pager.getOrder();
            }
            Sort.Direction direction= Sort.Direction.DESC;
            if(!StringUtils.isEmpty(pager.getDirection())){
                if(pager.getDirection().toUpperCase().equals("ASC")){
                    direction= Sort.Direction.ASC;
                }
            }
            pageable=PageRequest.of(page,pageSize,direction,orders);
        }
        return pageable;
    }
}
