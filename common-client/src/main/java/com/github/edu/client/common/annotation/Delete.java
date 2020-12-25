package com.github.edu.client.common.annotation;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.lang.annotation.*;

/**
 * 删除
 * Created by 王建 on 2017/6/14.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RequestMapping(value = "/delete",method = {RequestMethod.POST,RequestMethod.GET})
@ResponseBody
public @interface Delete {

}
