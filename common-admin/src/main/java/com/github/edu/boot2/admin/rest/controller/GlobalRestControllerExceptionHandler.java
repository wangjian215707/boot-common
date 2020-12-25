package com.github.edu.boot2.admin.rest.controller;

import com.github.admin.edu.assembly.exception.BaseException;
import com.github.edu.boot2.admin.rest.exception.CustomException;
import com.github.edu.boot2.admin.rest.exception.entity.Result;
import com.github.edu.boot2.admin.util.ResultStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

/**
 *
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/2/26
 */
@Slf4j
@RestControllerAdvice
public class GlobalRestControllerExceptionHandler {

    /**
     * 400 - Bad Request
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({HttpMessageNotReadableException.class, MissingServletRequestParameterException.class, BindException.class,
            ServletRequestBindingException.class, MethodArgumentNotValidException.class, ConstraintViolationException.class})
    public Result handleHttpMessageNotReadableException(Exception e) {
        log.error("参数解析失败", e);
        if (e instanceof BindException){
            return new Result(ResultStatusEnum.BAD_REQUEST.getCode(), ((BindException)e).getAllErrors().get(0).getDefaultMessage());
        }
        return new Result(ResultStatusEnum.BAD_REQUEST.getCode(), e.getMessage());
    }

    /**
     * 405 - Method Not Allowed
     * 带有@ResponseStatus注解的异常类会被ResponseStatusExceptionResolver 解析
     */
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("不支持当前请求方法", e);
        return new Result(ResultStatusEnum.METHOD_NOT_ALLOWED, null);
    }


    /**
     * 其他全局异常在此捕获
     * @param e
     * @return
     */
    @ExceptionHandler(Exception .class)
    public Object handleException(Exception  e) {
        log.error("服务运行异常", e);
        if (e instanceof CustomException) {//自定义异常
            return new Result(((CustomException) e).getCode(), e.getMessage());
        }
        if(e instanceof BaseException){
            return new Result(((BaseException) e).getStatus(), e.getMessage());
        }
        return new Result(ResultStatusEnum.SYSTEM_ERR, null);
    }
}
