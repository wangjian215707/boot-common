package com.github.edu.bsdt.jwt.server.controller;

import com.github.edu.bsdt.jwt.server.service.ClientJWTTokenManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 本类用于创建客户端token
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/15
 * Time: 21:48
 */
@RestController
@RequestMapping("/rest/api/edu/jwt")
public class ClientJWTTokenManagerRestController {

    @Autowired
    private ClientJWTTokenManagerService service;

    /**
     * 获取客户端token
     * @param clientId 客户端编号
     * @param pubKey 客户端公钥
     * @return 客户端token json 字符串
     */
    @GetMapping("/get/token")
    public String auth(@RequestParam("clientId")String clientId,
                       @RequestParam("pubKey")String pubKey){

        return service.auth(clientId,pubKey);
    }
}
