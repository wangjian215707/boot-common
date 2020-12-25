package com.github.edu.security.login.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-5-30
 */
public class ICECookieUtil {

    /**
     * 将令牌添加到cookie
     *
     * @param response
     * @param domain   作用域
     * @param token    令牌
     */
    public static void addCookie(HttpServletResponse response, String domain, String token) {
        Cookie cookie = null;
        try {
            cookie = new Cookie("iPlanetDirectoryPro", URLEncoder.encode(token, "gb2312"));
            cookie.setPath("/");
            cookie.setDomain(domain);
            cookie.setMaxAge(60 * 60*24);
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void addWeiXinCookie(HttpServletResponse response, String domain, String token) {
        Cookie cookie = null;
        cookie = new Cookie("CookieWDirectoryPro", token);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(60*60*24*365);
        response.addCookie(cookie);
    }

    /**
     * 删除令牌
     *
     * @param response
     * @param domain
     */
    public static void removeCookie(HttpServletResponse response, String domain) {
        Cookie cookie = null;
        cookie = new Cookie("iPlanetDirectoryPro", null);
        cookie.setPath("/");
        cookie.setDomain(domain);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
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
