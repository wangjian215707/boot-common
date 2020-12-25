package com.github.edu.security.login.configuration;

import com.github.edu.security.login.interceptor.IceInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-6-13
 */
@Configuration
public class ClientCustomWebConfiguration implements WebMvcConfigurer {

    public void addInterceptors(InterceptorRegistry registry){

        registry.addInterceptor(getUserLoginInterceptor()).addPathPatterns("/**").excludePathPatterns("/login","/s/**");
    }

    @Bean
    public IceInterceptor getUserLoginInterceptor(){
        return  new IceInterceptor();
    }
}
