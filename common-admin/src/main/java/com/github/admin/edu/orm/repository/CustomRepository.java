package com.github.admin.edu.orm.repository;
import com.github.admin.edu.assembly.page.entity.Pager;
import com.github.admin.edu.orm.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.util.List;

/**
 * 自定义JAP接口
 * Created by 王建 on 2017/6/9.
 */
@NoRepositoryBean
public interface CustomRepository<T,ID extends Serializable> extends JpaRepository<T,ID>,JpaSpecificationExecutor<T> {

    Page<T> findByAuto(T example, Pager pager);

    Page<T> findByAuto(T exanple, Integer current, Integer rows, List<OrderEntity> entities);

    Page<T> findByAuto(T exanple, List<OrderEntity> entities);

    long customCount(T example);

    int deleteByIds(Iterable<ID> ids);

    List<T> findByAuto(T example);

    List<T> findByAuto(T example, String direction, String... strings);


}
