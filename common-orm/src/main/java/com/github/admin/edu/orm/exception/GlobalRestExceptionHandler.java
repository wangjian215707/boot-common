package com.github.admin.edu.orm.exception;

import com.github.admin.edu.assembly.common.enm.ErrorEnum;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.xnio.Result;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/12/18
 */
@RestControllerAdvice
public class GlobalRestExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler(HttpServletRequest request, Exception e) {
        JsonEntity jsonEntity = null;
        if (e instanceof GlobalException) {
            GlobalException exception = (GlobalException) e;
            jsonEntity = exception.getJsonEntity();
        } else if (e instanceof BindException) {
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String msg = error.getDefaultMessage();
            jsonEntity = new JsonEntity();
            jsonEntity.setState(ErrorEnum.ERROR_SERVER_CSQS.getState());
            jsonEntity.setMsg(msg);
        }
        if (null == jsonEntity) {
            jsonEntity = new JsonEntity();
            jsonEntity.setState(ErrorEnum.ERROR_SERVER.getState());
            jsonEntity.setMsg(ErrorEnum.ERROR_SERVER.getMsg());
        }
        return JsonUtils.toJson(jsonEntity);
    }
}
