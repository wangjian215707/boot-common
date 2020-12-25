package com.github.edu.boot2.admin.security.interceptor;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.github.admin.edu.assembly.exception.auth.ClientTokenException;
import com.github.edu.boot2.admin.entity.TSysSystem;
import com.github.edu.boot2.admin.security.compnent.JWTComponent;
import com.github.edu.boot2.admin.security.jwt.JwtUserInformation;
import com.github.edu.boot2.admin.service.ISysSystemService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import com.github.edu.boot2.admin.util.NetworkErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import static com.github.edu.client.common.constant.CommonConstants.SYS_PRIVATE_KEY;
import static com.github.edu.client.common.constant.CommonConstants.SYS_PUBLIC_KEY;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 接口权限校验
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/22
 */
@Slf4j
public class HttpBasicAuthorizeInterceptor  extends HandlerInterceptorAdapter {

    @Autowired
    private ISysSystemService service;

    @Autowired
    private JWTComponent jwtComponent;

    /**
     * 自定义头 名称
     */
    private static final String USER_CODE_HEADER="u-code";//用户基本信息

    private static final String CLIENT_SECRET_HEADER="c-secret";//客户端密钥

    private static final String CLIENT_APPID_HEADER="c-appid";//客户端编号

    private static final String TOKEN_HEADER="U-TOKEN";//token值

    private static final String TOKEN_TYPE="u-type";//token类型

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String tokenType=request.getHeader(TOKEN_TYPE);//token值
        log.info("BasicAuthorInterceptor requestURI:"+request.getRequestURI());
        if(!StringUtils.isEmpty(tokenType)){
            if(ConstantEnum.ENUM_ID_TYPE_TOKEN_TYPE_USER.getNum()==(Integer.parseInt(tokenType))){//用户权限校验
                //todo::用户接口权限校验
                String token=request.getHeader(TOKEN_HEADER);//获取token值
                if(StringUtils.isEmpty(token)){
                    throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_NO_TOKEN.getCode(),NetworkErrorEnum.NETWORK_STATE_NO_TOKEN.getNum());
                }
                JwtUserInformation jwtUserInformation=jwtComponent.getUserInformation(token,SYS_PRIVATE_KEY);
                if(null!=jwtUserInformation){
                    request.setAttribute("userId",jwtUserInformation.userId());
                }
            }
            if(ConstantEnum.ENUM_ID_TYPE_TOKEN_TYPE_CLIENT.getNum()==(Integer.parseInt(tokenType))){//客户端权限校验
                String clientId=request.getHeader(CLIENT_APPID_HEADER);//客户端编号
                String clientSecret=request.getHeader(CLIENT_SECRET_HEADER);//客户端密钥
                if(StringUtils.isEmpty(clientId)||StringUtils.isEmpty(clientSecret)){
                    throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_NO_CODE.getCode(),NetworkErrorEnum.NETWORK_STATE_NO_CODE.getNum());
                }
                //接入系统权限校验
                TSysSystem tSysSystem=checkedSecret(clientId,clientSecret,Integer.parseInt(tokenType));
                if(null==tSysSystem){
                    throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_CLIENT_ERROR.getCode(), NetworkErrorEnum.NETWORK_STATE_CLIENT_ERROR.getNum());
                }
                //todo::客户端接口权限校验
            }
        }
        return true;
    }

    /**
     * 检查密钥
     *
     * @param appId
     * @param secret
     * @return
     */
    private TSysSystem checkedSecret(String appId, String secret, Integer type) {
        if (com.github.admin.edu.assembly.string.util.StringUtils.isNotBlank(appId) && com.github.admin.edu.assembly.string.util.StringUtils.isNotBlank(secret)) {
            //解析密钥
            RSA rsa = new RSA(SYS_PRIVATE_KEY, null);
            String key = rsa.decryptStr(secret, KeyType.PrivateKey);
            if (key.equals(appId)) {//验证密钥是否正确
                TSysSystem tSysSystem = service.getTSysStem(appId, type);
                if (null != tSysSystem) {
                    return tSysSystem;
                }
                throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_CLIENT_ERROR.getCode(), NetworkErrorEnum.NETWORK_STATE_CLIENT_ERROR.getNum());
            }
            throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_SECRET.getCode(), NetworkErrorEnum.NETWORK_STATE_SECRET.getNum());
        }
        throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_NO_CODE.getCode(), NetworkErrorEnum.NETWORK_STATE_NO_CODE.getNum());
    }
}
