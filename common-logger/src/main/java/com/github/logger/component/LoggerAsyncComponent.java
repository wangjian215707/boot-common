package com.github.logger.component;

import com.github.logger.entity.AspectEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 异步处理日志
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/12/18
 */
@Async
@Component
@Slf4j
public class LoggerAsyncComponent {

    public void doLog(AspectEntity aspectEntity){
        log.info(aspectEntity.toString());
    }
}
