package com.github.admin.edu.assembly.http.util;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 * user:王建
 * date:2018/03/12
 * httpClient4.5.x连接池设置
 */
public class HttpClientPool {


    private static final Logger LOG = LoggerFactory.getLogger(HttpClientPool.class);

    private static PoolingHttpClientConnectionManager manager = null;

    private static CloseableHttpClient httpClient = null;

    public static synchronized CloseableHttpClient getHttpClient() {
        if (null == httpClient) {
            // 在调用SSL之前需要重写验证方法，取消检测SSL
            X509TrustManager trustManager = new X509TrustManager() {
                @Override public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                @Override public void checkClientTrusted(X509Certificate[] xcs, String str) {}
                @Override public void checkServerTrusted(X509Certificate[] xcs, String str) {}
            };
            SSLContext ctx = null;
            try {
                ctx = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
                ctx.init(null, new TrustManager[] { trustManager }, null);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(ctx, NoopHostnameVerifier.INSTANCE);
            //注册访问协议相关的socket工厂
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", socketFactory)
                    .build();
            //httpConnection工厂：配置写请求/解析响应处理
            HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connectionFactory = new ManagedHttpClientConnectionFactory(
                    DefaultHttpRequestWriterFactory.INSTANCE, DefaultHttpResponseParserFactory.INSTANCE);
            //dns解析器
            DnsResolver dnsResolver = SystemDefaultDnsResolver.INSTANCE;
            //创建池化连接管理
            manager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connectionFactory, dnsResolver);
            //默认为Socket配置
            SocketConfig socketConfig = SocketConfig.custom().setTcpNoDelay(true).build();
            manager.setDefaultSocketConfig(socketConfig);
            manager.setMaxTotal(300);//设置整个连接吃的最大连接数
            manager.setDefaultMaxPerRoute(200);//设置每个路由的最大连接数
            manager.setValidateAfterInactivity(5 * 1000);//设置在从连接池去连接时，连接不活跃多长时间如要进行验证，默认2s
            //默认请求配置
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(2 * 1000)//设置连接超时时间2s
                    .setSocketTimeout(5 * 1000)//设置等待数据超市时间
                    .setConnectionRequestTimeout(2000)//设置从连接池获取连接超时时间
                    .build();
            //创建httpClient
            httpClient= HttpClients.custom()
                    .setConnectionManager(manager)
                    .setConnectionManagerShared(false)//设置连接池非共享模式
                    .evictIdleConnections(60, TimeUnit.SECONDS)
                    .evictExpiredConnections()//定期回收过期连接
                    .setConnectionTimeToLive(60,TimeUnit.SECONDS)//连接池存货时间，如果不设置，将根据长连接信息决定
                    .setDefaultRequestConfig(requestConfig)//设置默认请求配置
                    .setConnectionReuseStrategy(DefaultConnectionReuseStrategy.INSTANCE)//连接重用策略，即是否能keepAlive
                    .setKeepAliveStrategy(DefaultConnectionKeepAliveStrategy.INSTANCE)//长连接配置，即获取长连接生产多长时间
                    .setRetryHandler(new DefaultHttpRequestRetryHandler(0,false))//设置重试次数，默认3次，当前禁用重试
                    .build();
            //JVM停止或者重启时，关闭连接池，施放连接
            Runtime.getRuntime().addShutdownHook(new Thread(){
                @Override
                public void run() {
                    try {
                        httpClient.close();
                    } catch (IOException e) {
                        LOG.error("httpClient连接池关闭失败："+e.getMessage());
                    }
                }
            });
        }
        return httpClient;
    }

}
