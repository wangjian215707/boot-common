package com.github.edu.boot2.admin.security.auth.service.impl;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.github.admin.edu.assembly.common.util.UUIDUtils;
import com.github.admin.edu.assembly.date.util.DateFormatUtils;
import com.github.admin.edu.assembly.exception.auth.ClientTokenException;
import com.github.admin.edu.assembly.string.util.StringUtils;
import com.github.edu.boot2.admin.entity.*;
import com.github.edu.boot2.admin.security.auth.entity.*;
import com.github.edu.boot2.admin.security.auth.service.IApiAuthService;
import com.github.edu.boot2.admin.security.auth.util.SystemUtil;
import com.github.edu.boot2.admin.security.compnent.UserTokenComponent;
import com.github.edu.boot2.admin.service.ISysImageCodeService;
import com.github.edu.boot2.admin.service.ISysSystemService;
import com.github.edu.boot2.admin.service.ISysUserService;
import com.github.edu.boot2.admin.service.ISysUserTokenService;
import com.github.edu.boot2.admin.util.ConstantEnum;
import com.github.edu.boot2.admin.util.NetworkErrorEnum;
import com.wf.captcha.ArithmeticCaptcha;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.github.edu.client.common.constant.CommonConstants.JWT_KEY_NAME;
import static com.github.edu.client.common.constant.CommonConstants.JWT_USER_NAME;
import static com.github.edu.client.common.constant.CommonConstants.JWT_USER_PASSWORD;
import static com.github.edu.client.common.constant.CommonConstants.SYS_PRIVATE_KEY;



/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/14
 */
@Slf4j
@Service
public class ApiAuthServiceImpl implements IApiAuthService {

    @Autowired
    private ISysSystemService service;

    @Value("${server.custom.singleLogin}")
    private boolean singleLogin;

  /*  @Autowired
    private AuthenticationProvider provider;*/

    public ApiAuthServiceImpl(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }


    /**
     * 获取应用接口授权
     *
     * @param appId  应用id
     * @param secret 应用密钥
     * @return 获取token
     */
    @Override
    public JwtToken getApplicationToken(String appId, String secret) {
        TSysSystem tSysSystem = checkedSecret(appId, secret, ConstantEnum.ENUM_ID_TYPE_TOKEN_TYPE_CLIENT.getNum());
        if (null != tSysSystem) {
            String compactJws = Jwts.builder()
                    .setSubject(tSysSystem.getCode())
                    .claim(JWT_KEY_NAME, tSysSystem.getName())
                    .setExpiration(DateTime.now().plusSeconds(7200).toDate())
                    .signWith(SignatureAlgorithm.HS512, SYS_PRIVATE_KEY)
                    .compact();
            JwtToken token = new JwtToken();
            token.setAccessToken(compactJws);
            token.setExpiresIn(7200);
            return token;
        }
        throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_CLIENT_ERROR.getCode(), NetworkErrorEnum.NETWORK_STATE_CLIENT_ERROR.getNum());
    }

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysUserTokenService userTokenService;


    @Autowired
    private UserTokenComponent userTokenComponent;

    /**
     * 获取用户token
     * 客户端通过提供的公钥加密userId,生成code值，
     * 系统通过客户端私钥，解析code,获取用户userID
     *
     * @param appId
     * @param secret
     * @return
     */
    @Override
    public UserToken getUserTokenService(String appId, String secret, String code) {
        //检查客户端是否有效
        TSysSystem tSysSystem = checkedSecret(appId, secret, ConstantEnum.ENUM_ID_TYPE_TOKEN_TYPE_USER.getNum());
        if (null != tSysSystem) {
            //解密code
            String userId = decryCode(code, tSysSystem.getPriKey());
            if (StringUtils.isNotBlank(userId)) {
                //检查用户是否存在，且没有禁用
                TSysUser tSysUser = userService.getSecurityUser(userId);
                if (null != tSysUser) {
                    //开始加密生成token
                    //先检查数据库是否有有效token
                    TSysUserToken tSysUserToken = userTokenService.getUserToken(appId, userId);
                    if (null != tSysUserToken) {
                        UserToken token = new UserToken();
                        token.setExpiresIn(7200);
                        token.setToken(tSysUserToken.getToken());
                        return token;
                    } else {
                        try {
                            String startTime = DateFormatUtils.formatDate(DateTime.now().toDate(), "yyyyMMddHHmmss");
                            String endTime = DateFormatUtils.formatDate(DateTime.now().plusSeconds(7200).toDate(), "yyyyMMddHHmmss");
                            String compactJws=userToken(tSysUser);
                            UserToken tokens = new UserToken();
                            tokens.setExpiresIn(7200);
                            tokens.setToken(compactJws);
                            TSysUserToken token = new TSysUserToken();
                            token.setClientId(appId);
                            token.setUserId(userId);
                            token.setToken(compactJws);
                            token.setEndTime(Long.parseLong(endTime));
                            token.setStateTime(Long.parseLong(startTime));
                            userTokenComponent.saveUserToken(token);
                            return tokens;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }
                throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_USER_ERROR.getCode(), NetworkErrorEnum.NETWORK_STATE_USER_ERROR.getNum());
            }
            throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_USER_CODE_ERROR.getCode(), NetworkErrorEnum.NETWORK_STATE_USER_CODE_ERROR.getNum());
        }
        throw new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_CLIENT_ERROR.getCode(), NetworkErrorEnum.NETWORK_STATE_CLIENT_ERROR.getNum());
    }

    /**
     * 获取验证码信息
     *
     * @return
     */
    @Override
    public ImageCode getUserClientCode() {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);//分别为图片的宽度和高
        // 几位数运算，默认是两位
        captcha.setLen(2);
        // 获取运算的结果
        String result = captcha.text();
        String uuid= UUIDUtils.generateShortUuid();
        TSysImageCode tSysImageCode=new TSysImageCode();
        tSysImageCode.setText(result);
        tSysImageCode.setUuid(uuid);
        userTokenComponent.saveImageCode(tSysImageCode);
        ImageCode imageCode=new ImageCode();
        imageCode.setUuid(uuid);
        imageCode.setImg(captcha.toBase64());
        return imageCode;
    }

    @Autowired
    private ISysImageCodeService sysImageCodeService;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /*@Autowired
    private PasswordEncoder passwordEncoder;*/

    /**
     * 用户登录
     *
     * @param userLogin
     * @param request
     * @return
     */
    @Override
    public Map<String,Object> userAuthLogin(UserLogin userLogin, HttpServletRequest request) {
        Map<String,Object> map=new HashMap<>();
        if(null!=userLogin){
            if(StringUtils.isBlank(userLogin.getPassword())||StringUtils.isBlank(userLogin.getUsername())){//用户名密码不能为空
                throw  new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_USER_PASSWORD_UID_ERROR.getCode(),
                        NetworkErrorEnum.NETWORK_STATE_USER_PASSWORD_UID_ERROR.getNum());
            }
            if(StringUtils.isBlank(userLogin.getCode())){//验证码不能为空
                throw  new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_IMAGE_CODE_NULL_ERROR.getCode(),
                        NetworkErrorEnum.NETWORK_STATE_IMAGE_CODE_NULL_ERROR.getNum());
            }
            if(StringUtils.isBlank(userLogin.getUuid())){//uuid不能为空
                throw  new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_IMAGE_CODE_UUID_ERROR.getCode(),
                        NetworkErrorEnum.NETWORK_STATE_IMAGE_CODE_UUID_ERROR.getNum());
            }
            TSysImageCode tSysImageCode=sysImageCodeService.queryByUUID(userLogin.getUuid());
            if(null==tSysImageCode){
                throw  new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_IMAGE_CODE_UUID_ERROR.getCode(),
                        NetworkErrorEnum.NETWORK_STATE_IMAGE_CODE_UUID_ERROR.getNum());
            }
            userTokenComponent.delete(tSysImageCode.getId().toString());//删除验证码
            if(!tSysImageCode.getText().equals(userLogin.getCode())){//验证码错误
                throw  new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_IMAGE_CODE_ERROR.getCode(),
                        NetworkErrorEnum.NETWORK_STATE_IMAGE_CODE_ERROR.getNum());
            }
            // 密码解密
            String password=decryCode(userLogin.getPassword(),SYS_PRIVATE_KEY);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userLogin.getUsername(), password);
            Authentication authentication = authenticationManagerBuilder.getObject().
                    authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            SecurityUser securityUser= (SecurityUser) authentication.getPrincipal();
            TSysUser tSysUser=securityUser.getUser();
            String token=userToken(tSysUser);
            TSysUser tSysUser1=new TSysUser();
            tSysUser1.setUserId(tSysUser.getUserId());
            tSysUser1.setName(tSysUser.getName());
            map.put("token",token);
            map.put("user",tSysUser1);
            //开始保存在线用户信息
            TSysOnlineUser tSysOnlineUser=new TSysOnlineUser();
            try {
                tSysOnlineUser.setLoginTime(DateFormatUtils.formatDate(new Date(),"yyyy/MM/dd HH:mm:ss"));
                tSysOnlineUser.setIp(SystemUtil.getIp(request));
                tSysOnlineUser.setAddress(SystemUtil.getCityInfo(tSysOnlineUser.getIp()));
                tSysOnlineUser.setBrowser(SystemUtil.getBrowser(request));
                tSysOnlineUser.setUserId(userLogin.getUsername());
                tSysOnlineUser.setToken(token);
                userTokenComponent.saveOnlineUser(tSysOnlineUser);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            if(!singleLogin){//不支持多用户同时在线
                // 踢掉之前已经登录的token
                userTokenComponent.deleteOnlineUserExceptToken(userLogin.getUsername(),token);
            }
            return map;
        }
        throw  new ClientTokenException(NetworkErrorEnum.NETWORK_STATE_NO_CODE.getCode(),NetworkErrorEnum.NETWORK_STATE_NO_CODE.getNum());
    }

    /**
     * 检查密钥
     *
     * @param appId
     * @param secret
     * @return
     */
    public TSysSystem checkedSecret(String appId, String secret, Integer type) {
        if (StringUtils.isNotBlank(appId) && StringUtils.isNotBlank(secret)) {
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

    private String decryCode(String code, String privateKey) {

        if (StringUtils.isNotBlank(code)) {
            RSA rsa = new RSA(privateKey, null);
            return rsa.decryptStr(code, KeyType.PrivateKey);
        }
        return null;
    }

    private String userToken(TSysUser tSysUser){
        return  Jwts.builder()
                .setSubject(tSysUser.getUserId())
                .claim(JWT_USER_NAME, tSysUser.getName())
                .claim(JWT_USER_PASSWORD, tSysUser.getUserId())
                .setExpiration(DateTime.now().plusSeconds(7200).toDate())
                .signWith(SignatureAlgorithm.HS512, SYS_PRIVATE_KEY)
                .compact();
    }

/*
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        String code = "admin";
        RSA rsa = new RSA(null, pubKey);
        String token = rsa.encryptBase64(code, KeyType.PublicKey);
        System.out.println(token);
        RSA rsa1 = new RSA(priKey, null);
        String val = rsa1.decryptStr(token, KeyType.PrivateKey);
        System.out.println(val);
    }*/

   /*public static void main(String[] args) {
       String compactJws = Jwts.builder()
               .setSubject("1123")
               .claim(JWT_KEY_NAME,"wangjian")
               .setExpiration(DateTime.now().plusSeconds(7200).toDate())
               .signWith(SignatureAlgorithm.HS512, priKey)
               .compact();
       System.out.println(compactJws);
   }*/
   /* public static void main(String[] args) throws ParseException {
        System.out.println(DateFormatUtils.formatDate(DateTime.now().plusSeconds(7200).toDate(),"yyyyMMddHHmmss"));
    }*/
}
