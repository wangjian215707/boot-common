package com.github.edu.boot2.admin.security.compnent;

import com.github.edu.boot2.admin.security.jwt.JWTClientInformation;
import com.github.edu.boot2.admin.security.jwt.JWTInfo;
import com.github.edu.boot2.admin.security.jwt.JwtUserInfo;
import com.github.edu.boot2.admin.security.jwt.JwtUserInformation;
import com.github.edu.boot2.admin.security.util.RsaKeyUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

import static com.github.edu.client.common.constant.CommonConstants.*;

/**
 * JWT Token 解析
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/14
 * Time: 21:45
 */
@Component
public class JWTComponent {

    private static RsaKeyUtil rsaKeyHelper=new RsaKeyUtil();

    /**
     * 获取token中的客户端信息
     *
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public JWTClientInformation getClientInfoFromToken(String token, String pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        return new JWTInfo(body.getSubject(), getObjectValue(body.get(JWT_KEY_NAME)));
    }

    /**
     * 解析用户基本信息
     * @param token
     * @param pubKey
     * @return
     * @throws Exception
     */
    public JwtUserInformation getUserInformation(String token,String pubKey) throws Exception {
        Jws<Claims> claimsJws = parserToken(token, pubKey);
        Claims body = claimsJws.getBody();
        return new JwtUserInfo(body.getSubject(),getObjectValue(body.get(JWT_USER_NAME)),getObjectValue(body.get(JWT_USER_PASSWORD)));
    }


    /**
     * 公钥解析token
     *
     * @param token
     * @return
     * @throws Exception
     */
    public  Jws<Claims> parserToken(String token, String key) throws Exception {

        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
        return claimsJws;
    }

    public  String getObjectValue(Object obj){
        return obj==null?"":obj.toString();
    }

    /**
     * 字节转字符
     * @param b
     * @return
     */
    private  String toHexString(byte[] b) {
        return (new BASE64Encoder()).encodeBuffer(b);
    }

    /**
     * 字符转字节
     * @param s
     * @return
     * @throws IOException
     */
    private final byte[] toBytes(String s) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(s);
    }

}
