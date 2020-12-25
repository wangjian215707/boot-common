package com.github.edu.security.login.impl;
import com.github.admin.edu.assembly.http.util.HttpClientPool;
import com.github.edu.security.login.GetCasTGTService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/5
 * Time: 17:34
 */
@Service
@Slf4j
public class GetCasTGTServiceImpl implements GetCasTGTService {

    @Value("${server.custom.cas.server.host}")
    private String server;
    @Value("${server.custom.cas.server.accountTypeCode}")
    private String accountTypeCode;
    @Value("${server.custom.cas.server.customerCode}")
    private String customerCode;
    @Value("${server.custom.cas.server.resultType}")
    private String resultType;
    @Value("${server.custom.cas.client.service}")
    private String serviceUrl;

    @Override
    public String getTicketGrantingTicket(String username, String password, HttpServletRequest request) {
        HttpClient client = HttpClientPool.getHttpClient();
        log.info("---------username:"+username);
        log.info("----------------password:"+password);
        log.info("------------server:"+server);
        HttpPost post = new HttpPost(server);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username", username));
        formparams.add(new BasicNameValuePair("password", password));//"admin#xyxy#2018#"
        formparams.add(new BasicNameValuePair("customerCode", "1_28"));//2_32
        formparams.add(new BasicNameValuePair("accountTypeCode", "1_01"));//1
        formparams.add(new BasicNameValuePair("resultType", resultType));
        formparams.add(new BasicNameValuePair("serviceUrl", serviceUrl));
        String header=request.getHeader("User-Agent");
        post.setHeader("User-Agent",header);
        HttpResponse response = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            post.setEntity(entity);
            log.info("User-Agent--------:"+header);
            response = client.execute(post);
            String str = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("-----cas:"+str);
           // Matcher matcher = Pattern.compile(".*action=\".*/(.*?)\".*").matcher(str);

            /*if (matcher.matches()) {
                return matcher.group(1);
            }*/
            if(str.indexOf("exceptions")!=-1){
                return null;
            }
            return str;

        } catch (IOException e) {
            log.info(e.getMessage());
        } finally {
            post.releaseConnection();
        }

        return null;

    }

    @Override
    public String getTicketGrantingTicket(String username, String password) {
        HttpClient client = HttpClientPool.getHttpClient();
        HttpPost post = new HttpPost(server);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        formparams.add(new BasicNameValuePair("username", username));
        formparams.add(new BasicNameValuePair("password", password));//"admin#xyxy#2018#"
//        formparams.add(new BasicNameValuePair("customerCode", "1_28"));//2_32
//        formparams.add(new BasicNameValuePair("accountTypeCode", "1_01"));//1
//        formparams.add(new BasicNameValuePair("resultType", resultType));
//        formparams.add(new BasicNameValuePair("serviceUrl", serviceUrl));

        HttpResponse response = null;
        try {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            post.setEntity(entity);
            response = client.execute(post);
            String str = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("-----cas:"+str);
            Matcher matcher = Pattern.compile(".*action=\".*/(.*?)\".*").matcher(str);
            if (matcher.matches()) {
                return matcher.group(1);
            }
            return null;
        } catch (IOException e) {
            log.info(e.getMessage());
        } finally {
            post.releaseConnection();
        }
        return null;
    }

    @Override
    public String getSt(String tgt,String url) {
        HttpClient client = HttpClientPool.getHttpClient();
        HttpPost post = new HttpPost(server+"/"+tgt);
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        HttpResponse response = null;
        try {
            formparams.add(new BasicNameValuePair("service", url));
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            post.setEntity(entity);
            response = client.execute(post);
            String str = EntityUtils.toString(response.getEntity(), "UTF-8");
            log.info("-----ST:"+str);
            if(str.indexOf("exceptions")!=-1){
                return null;
            }
            return str;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            post.releaseConnection();
        }
        return null;
    }

}
