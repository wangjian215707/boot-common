package com.github.admin.edu.orm.repository.impl;

import com.github.admin.edu.assembly.page.entity.Pager;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.admin.edu.orm.entity.OrderEntity;
import com.github.admin.edu.orm.repository.CustomRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;
import java.util.List;


/**
 * Created by 王建 on 2017/6/9.
 */
public class CustomRepositoryImpl<T,ID extends Serializable> extends SimpleJpaRepository<T,ID> implements CustomRepository<T,ID> {

    private final EntityManager entityManager;

    public CustomRepositoryImpl(Class<T> domainClass,EntityManager entityManager){
        super(domainClass,entityManager);
        this.entityManager=entityManager;
    }

    @Transactional(readOnly = true)
    @Override
    public Page<T> findByAuto(T example, Pager pager) {

        Pageable pageable=CustomRepositoryPager.pageable(pager);
        return findAll(CustomSpecs.byAuto(entityManager,example),pageable);
    }

    @Override
    public Page<T> findByAuto(T exanple, Integer current, Integer rows, List<OrderEntity> entities) {
        Pageable pageable=CustomRepositoryPager.pageable(current,rows,entities);
        return findAll(CustomSpecs.byAuto(entityManager,exanple),pageable);
    }

    @Override
    public Page<T> findByAuto(T exanple, List<OrderEntity> entities) {
        Pageable pageable=CustomRepositoryPager.pageable(entities);
        return  findAll(CustomSpecs.byAuto(entityManager,exanple),pageable);
    }

    @Override
    public long customCount(T example) {
        return count(CustomSpecs.byAuto(entityManager,example));
    }

    @Transactional
    @Override
    public int deleteByIds(Iterable<ID> ids) {
        int i=0;
        if (ids == null || !ids.iterator().hasNext()) {
            return i;
        }
        for (ID id:ids){
            deleteById(id);
            i++;
        }
        return i;
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findByAuto(T example) {
        return findAll(CustomSpecs.byAuto(entityManager,example));
    }

    @Transactional(readOnly = true)
    @Override
    public List<T> findByAuto(T example, String direction, String... strings) {
        Sort.Direction directions= Sort.Direction.ASC;
        if(StringUtils.isNotBlank(direction)&&"DESC".equals(direction.toLowerCase())){
            directions=Sort.Direction.DESC;
        }
        if(null!=strings&&strings.length>0){
            return findAll(CustomSpecs.byAuto(entityManager,example),Sort.by(directions,strings));
        }
        return findByAuto(example);
    }


}
