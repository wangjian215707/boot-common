package com.github.edu.client.common.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-25
 */
@Component
public class SystemInformation {

    @Value("${server.custom.system.web.title}")
    private String title;//标题

    @Value("${server.custom.system.web.footer}")
    private String footer;//网站页脚信息

    @Value("${server.custom.system.web.logo}")
    private String logo;//网站logo

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
