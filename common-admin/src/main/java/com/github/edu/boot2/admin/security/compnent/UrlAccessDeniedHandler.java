package com.github.edu.boot2.admin.security.compnent;

import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.edu.boot2.admin.entity.UrlResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 无权限
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/11/22
 */
@Component
public class UrlAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e)
            throws IOException, ServletException {
        UrlResponse response = new UrlResponse();
        response.setSuccess(false);
        response.setCode(403);
        response.setMessage("Need Authorities!");
        httpServletResponse.setStatus(403);
        httpServletResponse.getWriter().write(JsonUtils.toJson(response));
    }
}
