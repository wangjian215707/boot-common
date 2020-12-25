package com.github.edu.security.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018/3/20
 * Cas 相关的配置类
 */
@Component
public class CasClientConfig {

    @Value("${server.custom.cas.client.host}")
    private String host;
    @Value("${server.custom.cas.client.login}")
    private String login;
    @Value("${server.custom.cas.client.logout}")
    private String logout;
    private Boolean sendRenew = false;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getLogout() {
        return logout;
    }

    public void setLogout(String logout) {
        this.logout = logout;
    }

    public Boolean getSendRenew() {
        return sendRenew;
    }

    public void setSendRenew(Boolean sendRenew) {
        this.sendRenew = sendRenew;
    }
}
