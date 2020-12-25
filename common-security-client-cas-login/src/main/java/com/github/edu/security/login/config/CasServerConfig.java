package com.github.edu.security.login.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018/3/21
 */
@Component
public class CasServerConfig {

    @Value("${server.custom.cas.server.host}")
    private String host;
    @Value("${server.custom.cas.server.login}")
    private String login;
    @Value("${server.custom.cas.server.logout}")
    private String logout;

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
}
