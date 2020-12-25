package com.github.edu.security.login.configuration;

import com.github.edu.security.login.interceptor.IceInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
 */
@Configuration
public class WebIceConfiguration  implements WebMvcConfigurer {


    @Value("${server.custom.ice.interceptor.path}")
    private String[] userInterceptorPath;

    @Value("${server.custom.ice.interceptor.exclude-path}")
    private String[] userExcludePath;


    public void addInterceptors(InterceptorRegistry registry){


        /***
         * 设置用户基本信息
         */
        registry.addInterceptor(getIceInterceptor()).
                addPathPatterns(userInterceptorPath).
                excludePathPatterns(userExcludePath);
    }

    @Bean
    public IceInterceptor getIceInterceptor(){
        return new IceInterceptor();
    }
}
