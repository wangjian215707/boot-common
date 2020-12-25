package com.github.edu.security.login.util;

import com.github.admin.edu.assembly.string.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/10/3
 * Time: 20:55
 */
@Slf4j
public class CookieManagerUtil {

    public static void addCookie(HttpServletResponse response, String token, String domain) {
        addCookie(response,null,token,domain);
    }

    public static void
    addCookie(HttpServletResponse response, String cookieName, String token, String domain) {
        Cookie cookie = null;
        if(StringUtils.isBlank(cookieName)){
            cookieName="iPlanetDirectoryPro";
        }
        try {
            log.info("------------cookieName:"+cookieName);
            cookie = new Cookie(cookieName, URLEncoder.encode(token, "gb2312"));
            cookie.setPath("/");
            cookie.setDomain(domain);
            cookie.setMaxAge(60*60*24*7);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static String getDecodedCookieValue(HttpServletRequest request, String cookieName)  {
        Cookie all_cookies[] = request.getCookies();
        Cookie myCookie;
        String decodedCookieValue = null;
        if (all_cookies != null) {
            for (int i = 0; i < all_cookies.length; i++) {
                myCookie = all_cookies[i];
                if (myCookie.getName().equals(cookieName)) {
                    try {
                        decodedCookieValue = URLDecoder.decode(myCookie.getValue(), "GB2312");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return decodedCookieValue;
    }
}
