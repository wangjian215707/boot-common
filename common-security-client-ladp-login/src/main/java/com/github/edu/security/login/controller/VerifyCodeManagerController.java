package com.github.edu.security.login.controller;

import com.github.edu.security.login.impl.VerifyCodeServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019/9/12
 */
@Controller
@Slf4j
public class VerifyCodeManagerController {

    @Value("${server.custom.verification-height}")
    private Integer verificationHeight;

    @Value("${server.custom.verification-width}")
    private Integer verificationWidth;

    @Autowired
    private VerifyCodeServiceImpl service;

    @RequestMapping("/s/verification")
    public void verification(HttpServletResponse response, HttpServletRequest request) {
        try {
            int width = verificationWidth;
            int height = verificationHeight;
            BufferedImage verifyImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            //生成对应宽高的初始图片
            String randomText = service.drawRandomText(width, height, verifyImg);
             //单独的一个类方法，出于代码复用考虑，进行了封装。
             //功能是生成验证码字符并加上噪点，干扰线，返回值为验证码字符
            request.getSession().setAttribute("IMAGE_SESSION_KEY", randomText);
            response.setContentType("image/png");//必须设置响应内容类型为图片，否则前台不识别
            OutputStream os = response.getOutputStream(); //获取文件输出流
            ImageIO.write(verifyImg, "png", os);//输出图片流
            os.flush();
            os.close();//关闭流
        } catch (IOException e) {
            log.error(e.getMessage());
            e.printStackTrace();

        }
    }
}
