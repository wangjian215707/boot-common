package com.github.edu.bsdt.jwt.client.common.configuration;

import com.github.edu.bsdt.jwt.client.common.interceptor.FeignClientResultInterceptor;
import feign.Logger;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/16
 * Time: 0:33
 */
@Configuration
public class FeginConfiguration {


    @Bean
    Logger.Level feignLoggerLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public FeignClientResultInterceptor getFeignClientResultInterceptor(){
        return new FeignClientResultInterceptor();
    }

    @Bean
    @Primary
    @Scope("prototype")
    public Encoder multipartFormEncoder() {
        return new SpringFormEncoder();
    }
}
