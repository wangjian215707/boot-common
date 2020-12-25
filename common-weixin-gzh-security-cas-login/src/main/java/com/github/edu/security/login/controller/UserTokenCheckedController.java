package com.github.edu.security.login.controller;

import com.github.edu.security.login.util.TickedUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-28
 */
@Controller
public class UserTokenCheckedController {

    // 工具类
    //Util util = new Util();

    @GetMapping("/s/token")
    @ResponseBody
    public String CheckWxServiceMsg(@RequestParam(value = "signature", defaultValue = "null") String signature,
                                    @RequestParam(value = "timestamp", defaultValue = "null") String timestamp,
                                    @RequestParam(value = "nonce", defaultValue = "null") String nonce,
                                    @RequestParam(value = "echostr", defaultValue = "null") String echostr,
                                    HttpServletResponse response) {
        PrintWriter print;
        if (signature != null && TickedUtil.checkSignature(signature, timestamp, nonce)) {
            try {
                print = response.getWriter();
                print.write(echostr);
                print.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
