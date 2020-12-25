package com.github.configuration;

import com.netflix.discovery.shared.Applications;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 用户监听Eureka服务Listener
 *
 */
@Configuration
@EnableScheduling
@Slf4j
public class EurekaInstanceCanceledListener implements ApplicationListener {

    /**
     * 1、EurekaInstanceCanceledEvent 失效事件
     * 2、EurekaInstanceRegisteredEvent 服务注册事件
     * 3、EurekaInstanceRenewedEvent 心跳检测服务
     * 4、EurekaRegistryAvailableEvent 可用事件
     * 5、EurekaServerStartedEvent 服务启动事件
     * @param applicationEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        //对挂掉的服务进行监听
        if (applicationEvent instanceof EurekaInstanceCanceledEvent){
            EurekaInstanceCanceledEvent canceledEvent=(EurekaInstanceCanceledEvent) applicationEvent;
            //获取当前Eureka实例中的节点信息
            PeerAwareInstanceRegistry registry= EurekaServerContextHolder.getInstance().getServerContext().getRegistry();
            Applications applications=registry.getApplications();
            //遍历全部注册节点，并匹配出已失效的节点
            applications.getRegisteredApplications().forEach((registryApplications)->{
                registryApplications.getInstances().forEach((instanceInfo -> {
                    if(instanceInfo.getInstanceId().equals(canceledEvent.getServerId())){
                        log.info("服务："+instanceInfo.getAppName()+"已经停止工作！");
                    }
                }));
            });
        }
        //对新注册的服务进行监听
        if(applicationEvent instanceof EurekaInstanceRegisteredEvent){
            EurekaInstanceRegisteredEvent registeredEvent=(EurekaInstanceRegisteredEvent) applicationEvent;
            log.info("服务："+registeredEvent.getInstanceInfo().getAppName()+"注册成功！");
        }
        //心跳检测服务
        if(applicationEvent instanceof EurekaInstanceRenewedEvent){
            EurekaInstanceRenewedEvent renewedEvent=(EurekaInstanceRenewedEvent) applicationEvent;
            log.info("开始对："+renewedEvent.getInstanceInfo().getAppName()+"服务进行心跳检测！");
        }
        //可用事件
        if(applicationEvent instanceof EurekaRegistryAvailableEvent){
            log.info("服务可用");
        }
        //服务启动事件
        if(applicationEvent instanceof EurekaServerStartedEvent){
            log.info("注册中心已经启动！");
        }
    }

}
