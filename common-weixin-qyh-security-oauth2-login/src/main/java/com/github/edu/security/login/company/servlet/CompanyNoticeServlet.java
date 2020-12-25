package com.github.edu.security.login.company.servlet;

import com.github.edu.security.login.company.util.AttestationUtil;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/10
 */
@WebServlet("/s/wx/notice/attestation")
public class CompanyNoticeServlet  extends HttpServlet {

    private static final long serialVersionUID = -8685285401859800066L;
    @Value("${weixin.custom.notice.token}")
    private String token;

    @Value("${weixin.custom.notice.encoding-aes-key}")
    private String aesKey;

    @Value("${weixin.custom.notice.corpid}")
    private String corpId;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AttestationUtil.verify(req,resp, token,aesKey,corpId);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
