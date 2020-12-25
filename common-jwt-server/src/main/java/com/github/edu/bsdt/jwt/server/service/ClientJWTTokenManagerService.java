package com.github.edu.bsdt.jwt.server.service;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/15
 * Time: 21:52
 */
public interface ClientJWTTokenManagerService {

    /**
     * 获取用户token
     * @param clientId 客户端id
     * @param pubKey 客户端公钥
     * @return token json字符串
     */
    String auth(String clientId, String pubKey);

}
