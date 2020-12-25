package com.github.edu.client.common.annotation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.lang.annotation.*;

/**
 * 自定义添加注解
 * Created by 王建 on 2017/6/13.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(value = "/new",method = {RequestMethod.GET})
public @interface New {
}
