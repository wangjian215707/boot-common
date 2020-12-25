package com.github;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/4
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaService {

    public static void main(String[] args) {

        SpringApplication.run(EurekaService.class,args);
    }

}
