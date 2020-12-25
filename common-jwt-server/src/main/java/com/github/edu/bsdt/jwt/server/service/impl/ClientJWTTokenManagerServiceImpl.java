package com.github.edu.bsdt.jwt.server.service.impl;

import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.jwt.entity.JwtTokenEntity;
import com.github.edu.bsdt.jwt.server.domain.ClientJWTTokenManagerDomain;
import com.github.edu.bsdt.jwt.server.entity.TSysJwt;
import com.github.edu.bsdt.jwt.server.service.ClientJWTTokenManagerService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.List;

import static com.github.edu.client.common.constant.CommonConstants.JWT_KEY_NAME;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/15
 * Time: 21:53
 */
@Slf4j
@Service
public class ClientJWTTokenManagerServiceImpl implements ClientJWTTokenManagerService {

    @Value("${server.custom.token.jwt.pri-key}")
    private String priKey;


    @Autowired
    private ClientJWTTokenManagerDomain domain;

    @Cacheable(value = "jwt_token", key = "#clientId")
    @Override
    public String auth(String clientId, String pubKey) {
        JwtTokenEntity jwtTokenEntity = null;
        if (StringUtils.isEmpty(clientId) || StringUtils.isEmpty(pubKey) || "null".equals(clientId) || "null".equals(pubKey)) {
            jwtTokenEntity = new JwtTokenEntity("客户端编号为空或者客户端公钥为空！", 4002);
            log.info("-------------:获取token失败，没有获取到客户端编号或者公钥！");
            return JsonUtils.toJson(jwtTokenEntity);
        }
        List<TSysJwt> list = domain.getAllByIdAndAndPubKeyAndState(clientId, pubKey);
        if (null != list && list.size() > 0) {
            TSysJwt sysJwt = list.get(0);
            if (null == sysJwt.getAge()) {//如果没有设置有效期，有效期时间为7200秒
                sysJwt.setAge(7200);
            }
            String compactJws = Jwts.builder()
                    .setSubject(sysJwt.getId())
                    .claim(JWT_KEY_NAME, sysJwt.getName())
                    .setExpiration(DateTime.now().plusSeconds(sysJwt.getAge()).toDate())
                    .signWith(SignatureAlgorithm.HS512, priKey)
                    .compact();
            jwtTokenEntity = new JwtTokenEntity();
            jwtTokenEntity.setToken(compactJws);
            jwtTokenEntity.setAge(sysJwt.getAge());
            return JsonUtils.toJson(jwtTokenEntity);
        }
        jwtTokenEntity = new JwtTokenEntity("客户端不存在或者没有权限！");
        log.info("-------------:客户端不存在，或者没有权限！");
        return JsonUtils.toJson(jwtTokenEntity);
    }

    /**
     * 字节转字符
     *
     * @param b
     * @return
     */
    private String toHexString(byte[] b) {
        return (new BASE64Encoder()).encodeBuffer(b);
    }

    /**
     * 字符转字节
     *
     * @param s
     * @return
     * @throws IOException
     */
    private final byte[] toBytes(String s) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(s);
    }
}
