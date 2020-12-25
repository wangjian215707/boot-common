package com.github.edu.bsdt.jwt.client.configuration;

import com.github.edu.bsdt.jwt.client.interceptor.HttpBasicAuthorizeInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/14
 * Time: 22:54
 */
@Configuration
public class JWTClientConfiguration implements WebMvcConfigurer {

    @Value("${server.custom.token.jwt.path}")
    public String[] path;//需要验证的地址

    @Value("${server.custom.token.jwt.excludePath}")
    public String[] excludePath;//不需要验证的地址

    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(getHttpBasicAuthorizeInterceptor())
                .addPathPatterns(path)
                .excludePathPatterns(excludePath);
    }

    @Bean
    public HttpBasicAuthorizeInterceptor getHttpBasicAuthorizeInterceptor(){

        return new HttpBasicAuthorizeInterceptor();
    }
}
