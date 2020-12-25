package com.github.edu.security.login.company.util;
import com.github.edu.security.login.company.ase.AesException;
import com.github.edu.security.login.company.ase.WXBizMsgCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

/**
 * 企业微信认证，及报文加密，解密
 * Created by Intellij IDE
 * User wangjian
 * Date 2017/10/24
 * Time 15:16
 * Version V1.01
 */
public class AttestationUtil {

    /**
     * 微信认证使用
     * @param request
     * @param response
     * @param token
     * @param encodingAESKey
     * @param corpId
     * @throws ServletException
     * @throws IOException
     */
    public static void verify(HttpServletRequest request, HttpServletResponse response, String token, String encodingAESKey, String corpId)
            throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html");
        PrintWriter out=response.getWriter();
        String sEchoStr= verifyURL(request,token,encodingAESKey,corpId);
        out.flush();
        out.print(sEchoStr);
        out.close();
    }
    /**
     * 微信报文处理
     * @param request
     * @return
     */
    public static String CompanyAttestation(HttpServletRequest request, String type, String value, String token, String encodingAESKey, String corpId){
        String sEchoStr=null;
        try {
            WXBizMsgCrypt wxcpt = new WXBizMsgCrypt(token,encodingAESKey,corpId);
            //微信加密签名，msg_signature结合了企业填写的token、请求中的timestamp、nonce参数、加密的消息体
            String msg_signature=request.getParameter("msg_signature");
            //时间戳
            String timestamp=request.getParameter("timestamp");
            //随机数
            String nonce=request.getParameter("nonce");
            //加密的随机字符串，以msg_encrypt格式提供。需要解密并返回echostr明文，
            // 解密后有random、msg_len、msg、$CorpID四个字段，其中msg即为echostr明文
            if ("VerifyURL".equals(type)) {//服务端认证解密
                sEchoStr=wxcpt.VerifyURL(msg_signature,timestamp,nonce,value);
            }else if("DecryptMsg".equals(type)){//客户端返回报文解密
                sEchoStr=wxcpt.DecryptMsg(msg_signature,timestamp,nonce,value);
            }else if("EncryptMsg".equals(type)){//加密
                sEchoStr=wxcpt.EncryptMsg(value,timestamp,nonce);
            }
        } catch (AesException e) {
            e.printStackTrace();
        }
        return sEchoStr;
    }

    /**
     * 获取客户端返回报文
     * @param request
     * @return
     */
    public static String getRoleBak(HttpServletRequest request, String token, String encodingAESKey, String corpId){
        String info= getInputString(request);
        return CompanyAttestation(request,"DecryptMsg",info,token,encodingAESKey,corpId);
    }



    /**
     * 微信系统认证
     * @param request
     * @return
     */
    public static String verifyURL(HttpServletRequest request, String token, String encodingAESKey, String corpId){
        return CompanyAttestation(request,"VerifyURL",request.getParameter("echostr"),token,encodingAESKey,corpId);
    }

    /**
     * 加密发送客户端报文
     * @param request
     * @param body
     * @return
     */
    public static String getReturnBak(HttpServletRequest request, String body, String token, String encodingAESKey, String corpId){
        return CompanyAttestation(request,"EncryptMsg",body,token,encodingAESKey,corpId);
    }
    /**
     * 请求获取报文
     * @param request
     * @return
     */
    public static String getInputString(HttpServletRequest request){
        StringBuffer buffer=new StringBuffer("");
        InputStreamReader reader=null;
        BufferedReader bufferedReader=null;
        try {
            reader=new InputStreamReader(request.getInputStream(),"utf-8");
            bufferedReader=new BufferedReader(reader);
            String info=null;
            while (null!=(info=bufferedReader.readLine())){
                buffer.append(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(null!=reader){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(null!=bufferedReader){
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return buffer.toString();
    }
}
