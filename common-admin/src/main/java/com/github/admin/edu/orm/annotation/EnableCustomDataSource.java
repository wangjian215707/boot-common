package com.github.admin.edu.orm.annotation;

import java.lang.annotation.*;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-11
 * 自定义注解，用于动态切换数据源
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnableCustomDataSource {
    String value() default "";
}
