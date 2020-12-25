package com.github.edu.security.login;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/5
 * Time: 17:33
 */
public interface GetCasTGTService {
    String getTicketGrantingTicket(String username, String password, HttpServletRequest request);

    String getTicketGrantingTicket(String username, String password);

    String getSt(String tgt, String url);
}
