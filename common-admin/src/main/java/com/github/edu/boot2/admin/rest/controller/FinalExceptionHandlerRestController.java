package com.github.edu.boot2.admin.rest.controller;

import com.github.edu.boot2.admin.rest.exception.entity.Result;
import com.github.edu.boot2.admin.util.ResultStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
@RestController
@Slf4j
public class FinalExceptionHandlerRestController implements ErrorController {
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping(value = "/error")
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Object error(HttpServletRequest request, HttpServletResponse response) {

        log.error("response error,httpCode:" + response.getStatus());
        // 错误处理逻辑
        int status = response.getStatus();
        if (status == 404) {
            return new Result(ResultStatusEnum.REQUEST_NOT_FOUND, "地址不存在");
        } else if (status == 500) {
            return new Result(ResultStatusEnum.SYSTEM_ERR, "系统错误！");
        } else if (status >= 100 && status < 200) {
            return new Result(ResultStatusEnum.HTTP_ERROR_100, null);
        } else if (status >= 300 && status < 400) {
            return new Result(ResultStatusEnum.HTTP_ERROR_300, null);
        } else if (status >= 400 && status < 500) {
            return new Result(ResultStatusEnum.HTTP_ERROR_400, null);
        } else {
            return new Result(ResultStatusEnum.SYSTEM_ERR, "系统错误！");
        }
    }
}
