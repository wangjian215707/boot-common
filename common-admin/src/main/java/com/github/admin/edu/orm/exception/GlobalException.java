package com.github.admin.edu.orm.exception;

import com.github.admin.edu.assembly.common.enm.ErrorEnum;
import com.github.admin.edu.assembly.common.entity.JsonEntity;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/12/18
 */
@Slf4j
@Data
public class GlobalException extends RuntimeException {

    private JsonEntity jsonEntity;

    public GlobalException(JsonEntity jsonEntity){
        this.jsonEntity=jsonEntity;
    }

    public GlobalException(){
        JsonEntity jsonEntity=new JsonEntity();
        jsonEntity.setState(ErrorEnum.ERROR_SERVER.getState());
        jsonEntity.setMsg(ErrorEnum.ERROR_SERVER.getMsg());
        this.jsonEntity=jsonEntity;
    }

}
