package com.github.edu.client.common.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 查看
 * Created by 王建 on 2017/6/14.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(value = "/{id}/entity",method = {RequestMethod.GET})
public @interface Open {
}
