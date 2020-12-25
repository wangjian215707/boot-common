package com.github.logger.entity;

import com.github.admin.edu.assembly.date.util.DateFormatUtils;
import lombok.Data;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/12/18
 */
@Data
public class AspectEntity implements Serializable {

    private String functionName;//方法名称

    private String packageName;//包名

    private String parameter;//参数

    private String parameterValue;//参数值

    private String requestUrl;//请求地址

    private String requestMethod;//请求类型

    private String requestClientIp;//请求ip地址

    private String requestTime;//请求时间

    {
        try {
            requestTime = DateFormatUtils.formatDate(new Date(),"yyyy/MM/dd HH:mm:ss");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    private String classMethod;//类名称


}
