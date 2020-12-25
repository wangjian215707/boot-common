package com.github.edu.client.common.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 编辑
 * Created by 王建 on 2017/6/14.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(value = "/{id}/edit",method = {RequestMethod.GET})
public @interface Edit {
}
