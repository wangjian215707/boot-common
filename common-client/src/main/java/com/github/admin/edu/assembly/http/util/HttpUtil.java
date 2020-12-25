package com.github.admin.edu.assembly.http.util;

import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.string.util.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/3/12
 * Time: 21:32
 * 一般用于http请求
 */
@Component
public class HttpUtil {

    private static final Logger LOG= LoggerFactory.getLogger(HttpUtil.class);

    /**
     * Http post请求，参数使用json格式
     * @param url
     * @return
     */
    public  String responseJsonServicePost(String url) {
        return responseJsonServicePost(url, null,null);
    }
    public String responseJsonServicePost(String url,String type){
        return responseJsonServicePost(url, null,type);
    }

    public String responseJsonServicePostString(String url,String date){
        String str = null;
        CloseableHttpClient httpClient = HttpClientPool.getHttpClient();
        CloseableHttpResponse response = null;
        try {
            StringEntity entity = new StringEntity(date, "UTF-8");
            entity.setContentEncoding("UTF-8");
            entity.setContentType("application/json");//发送json数据
            HttpPost post = new HttpPost(url);
            post.setEntity(entity);
            //开始发送请求
            response = httpClient.execute(post);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("UnsupportedEncodingException:"+e.getMessage());
        } catch (ClientProtocolException e) {
            LOG.error("ClientProtocolException:"+e.getMessage());
        } catch (IOException e) {
            LOG.error("IOException:"+e.getMessage());
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error("IOException:"+e.getMessage());
                }
            }
        }
        return str;
    }

    public String responseServicePost(String url, Map<String, Object> dataMap){
        String str=null;
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        CloseableHttpClient httpClient = HttpClientPool.getHttpClient();
        CloseableHttpResponse response = null;
        /*设置参数*/
        if (null != dataMap && !"".equals(dataMap)) {
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

        }
        try {
            HttpEntity httpEntity=new UrlEncodedFormEntity(formparams, "UTF-8");

            HttpPost post = new HttpPost(url);
            post.setEntity(httpEntity);

            response = httpClient.execute(post);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("UnsupportedEncodingException:"+e.getMessage());
        } catch (ClientProtocolException e) {
            LOG.error("ClientProtocolException:"+e.getMessage());
        } catch (IOException e) {
            LOG.error("IOException:"+e.getMessage());
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error("IOException:"+e.getMessage());
                }
            }
        }

        return str;
    }

    public String responseServicePost(String url,Map<String,Object> dataMap,Map<String,String> headers){
        String str = null;
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        CloseableHttpClient httpClient = HttpClientPool.getHttpClient();
        CloseableHttpResponse response = null;
        /*设置参数*/
        if (null != dataMap && !"".equals(dataMap)) {
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        try {
            HttpEntity httpEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost post = new HttpPost(url);
            post.setEntity(httpEntity);
            if(null!=headers&&!"".equals(headers)){
                for (Map.Entry<String,String> entry:headers.entrySet()){
                    post.addHeader(entry.getKey(),entry.getValue());
                }
            }
            response = httpClient.execute(post);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
    /**
     * 向服务端发起请求，并且参数已json形式传输
     *
     * @param url     请求地址
     * @param dataMap 请求参数
     * @return 服务端返回结果
     */
    public  String
    responseJsonServicePost(String url, Map<String, Object> dataMap,String type) {
        String str = null;
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        CloseableHttpClient httpClient = HttpClientPool.getHttpClient();
        CloseableHttpResponse response = null;
        /*设置参数*/
        if (null != dataMap && !"".equals(dataMap)) {
            for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
        }
        try {
            StringEntity entity = new StringEntity(JsonUtils.toJson(dataMap), "UTF-8");
            entity.setContentEncoding("UTF-8");
            if(StringUtils.isBlank(type)){
                entity.setContentType("application/json");//发送json数据
            }else {
                entity.setContentType(type);
            }

            HttpPost post = new HttpPost(url);
            post.setEntity(entity);
            //开始发送请求
            response = httpClient.execute(post);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("UnsupportedEncodingException:"+e.getMessage());
        } catch (ClientProtocolException e) {
            LOG.error("ClientProtocolException:"+e.getMessage());
        } catch (IOException e) {
            LOG.error("IOException:"+e.getMessage());
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error("IOException:"+e.getMessage());
                }
            }
        }
        return str;
    }
    /**
     * 向服务端发起请求，并且参数已json形式传输
     *
     * @param url     请求地址
     * @return 服务端返回结果
     */
    public  String responseServiceGet(String url) {
        String str = null;
        HttpClient httpClient = HttpClientPool.getHttpClient();
        HttpResponse response = null;
        try {
            HttpGet get = new HttpGet(url);
            //开始发送请求
            response = httpClient.execute(get);
            str = EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            LOG.error("UnsupportedEncodingException:"+e.getMessage());
        } catch (ClientProtocolException e) {
            LOG.error("ClientProtocolException:"+e.getMessage());
        } catch (IOException e) {
            LOG.error("IOException:"+e.getMessage());
        } finally {
            if (null != response) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    LOG.error("IOException:"+e.getMessage());
                }
            }
        }
        return str;
    }
}
