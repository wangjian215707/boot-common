package com.github.admin.edu.assembly.exception.handler;

import com.github.admin.edu.assembly.exception.auth.ClientTokenException;
import com.github.admin.edu.assembly.exception.entity.BaseResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-20
 */
@ControllerAdvice("com.github")
@ResponseBody
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ClientTokenException.class)
    public BaseResponse clientTokenException(HttpServletResponse response, ClientTokenException ex){
        response.setStatus(403);
        logger.error(ex.getMessage(),ex);
        return new BaseResponse(ex.getStatus(), ex.getMessage());
    }
}
