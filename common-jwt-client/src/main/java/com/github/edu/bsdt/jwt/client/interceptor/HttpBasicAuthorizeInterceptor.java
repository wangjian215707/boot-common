package com.github.edu.bsdt.jwt.client.interceptor;

import com.github.admin.edu.assembly.exception.auth.ClientTokenException;
import com.github.edu.bsdt.jwt.client.component.JWTComponent;
import com.github.edu.bsdt.jwt.client.service.JWTClientInformation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 普通对请求进行拦截，获取自定义请求头信息，
 * 验证头信息中的token信息
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/14
 * Time: 22:18
 */
@Slf4j
public class HttpBasicAuthorizeInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JWTComponent jwtComponent;

    /**
     * 自定义头 名称
     */
    @Value("${server.custom.token.header.token-header}")
    private String tokenHeader;//token

    @Value("${server.custom.token.header.client-id}")
    private String clientId;//key

    @Value("${server.custom.token.jwt.pri-key}")
    private String priKey;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token=request.getHeader(tokenHeader);//token值

        String client_Id=request.getHeader(clientId);//公钥key值

        if(StringUtils.isEmpty(token)||StringUtils.isEmpty(client_Id)){
            log.error("没有获取到客户端的token值或者clientId值:You didn't get your token or clientId !!!!!!");
            throw new ClientTokenException("You didn't get your token or clientId !!!!!! ");
        }
        try {
            JWTClientInformation jwtClientInformation=jwtComponent.getInfoFromToken(token,priKey);
            String cid=jwtClientInformation.getClientId();
            //TODO::这里可以后期扩展，根据cid编号，查询授权信息
            String cname=jwtClientInformation.getClientNAME();
            log.info("客户端："+cname+"验证token成功！");
        }catch (Exception e){
            log.error("token验证失败！没有权限调用此服务！：You do not have permission to access secondary services !!!!!!");
            throw new ClientTokenException("You do not have permission to access secondary services !!!!!!");
        }


        return true;
    }

}
