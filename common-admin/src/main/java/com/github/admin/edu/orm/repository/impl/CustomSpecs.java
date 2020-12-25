package com.github.admin.edu.orm.repository.impl;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.SingularAttribute;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import com.google.common.collect.Iterables;


/**
 * 自定义一个分页方法
 * Created by 王建 on 2017/6/9.
 */
public class CustomSpecs {

    public static <T>Specification<T> byAuto(final EntityManager entityManager, final T example){
        /**获得当前实体类的对象类型**/
        if(null==example){
            return new Specification<T>() {
                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    return criteriaBuilder.conjunction();
                }
            };
        }else {
            final Class<T> type=(Class<T>) example.getClass();
            return new Specification<T>() {
                @Override
                public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicates=new ArrayList<>();
                    EntityType<T> entityType=entityManager.getMetamodel().entity(type);//反射获取实体对象，对象信息
                    for (Attribute<T,?> attr:entityType.getDeclaredAttributes()){
                        String name=attr.getName();//对象名称
                        Object attrValue=getValue(example,name);//根据对象名称，反射获取对象数据
                        if(null!=attrValue){
                            if(attr.getJavaType()==String.class){//对象为String 类型
                                if(!StringUtils.isEmpty(attrValue)){
                                    /**
                                     * 对String 类型的数据进行判断，如果like,date等关键字，分别执行不同的查询方法
                                     */
                                    String val= (String) attrValue;
                                    if(val.startsWith("like:")){//模糊查询
                                        val=val.replace("like:","");
                                        predicates.add(criteriaBuilder.like(root.get(attribute(entityType,attr.getName(),String.class)),pattern(val)));
                                    }else if(val.startsWith("date:")){//日期开头
                                        val=val.replace("date:","");
                                        if(val.indexOf(";")!=-1){//根据时间范围查询
                                            String[] vals=val.split(";");
                                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(attribute(entityType,attr.getName(),String.class)),vals[0]));
                                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(attribute(entityType,attr.getName(),String.class)),vals[1]));
                                        }else if(val.startsWith("x-")){//小于
                                            val=val.replace("x-","");
                                            predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(attribute(entityType,attr.getName(),String.class)),val));
                                        }else if(val.startsWith("d-")){//大于
                                            val=val.replace("d-","");
                                            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(attribute(entityType,attr.getName(),String.class)),val));
                                        }
                                    }else {
                                        predicates.add(criteriaBuilder.equal(root.get(attribute(entityType,attr.getName(),attrValue.getClass())),attrValue));
                                    }

                                }
                            }else{
                                predicates.add(criteriaBuilder.equal(root.get(attribute(entityType,attr.getName(),attrValue.getClass())),attrValue));
                            }
                        }
                    }
                    return predicates.isEmpty()?criteriaBuilder.conjunction():criteriaBuilder.and(Iterables.toArray(predicates,Predicate.class));
                }
                /**
                 * 反射获取对象属性
                 * 获取实体类的当前属性SingularAttribute,
                 * SingularAttribute中包含的是实体类中某个单独属性
                 * @param entityType
                 * @param fieldName
                 * @param fieldClass
                 * @param <T>
                 * @param <E>
                 * @return
                 */
                private <T,E>SingularAttribute<T,E> attribute(EntityType<T> entityType, String fieldName, Class<E> fieldClass){
                    return entityType.getDeclaredSingularAttribute(fieldName,fieldClass);
                }
                /**
                 * 获取实体对象属性值
                 * @param example
                 * @param name
                 * @return
                 */
                private Object getValue(T example,String name){
                    Object attrValue=null;
                    if(!StringUtils.isEmpty(name)){
                        name = name.substring(0, 1).toUpperCase() + name.substring(1);
                        try {
                            Method method=type.getMethod("get"+name);
                            attrValue=method.invoke(example);
                        } catch (NoSuchMethodException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                    return attrValue;
                }
            };
        }
    }
    /**
     * 字符添加模糊查询通配符
     * @param str
     * @return
     */
    static private String pattern(String str){
        return "%"+str+"%";
    }
}
