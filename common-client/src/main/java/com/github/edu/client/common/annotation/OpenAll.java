package com.github.edu.client.common.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 查询全部
 * Created by 王建 on 2017/6/14.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(value = "/all",method = {RequestMethod.GET})
public @interface OpenAll {

}
