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
 * Created by Intellij IDE
 * User wangjian
 * Date 2017/10/24
 * Time 15:23
 * Version V1.01
 */
@WebServlet("/s/wx/attestation")
public class CompanyServlet extends HttpServlet {

    private static final long serialVersionUID = -8685285401859800066L;

    @Value("${weixin.custom.token}")
    private String token;
    @Value("${weixin.custom.encoding-aes-key}")
    private String aesKey;
    @Value("${weixin.custom.corpid}")
    private String corpId;
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AttestationUtil.verify(req,resp, token,aesKey,corpId);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
