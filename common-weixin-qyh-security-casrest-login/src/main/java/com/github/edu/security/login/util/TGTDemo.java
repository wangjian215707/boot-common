package com.github.edu.security.login.util;

import java.util.HashMap;
import java.util.Map;

public class TGTDemo {
  public static String createTGT(String username, String password) {
    Map params = new HashMap(16);
    params.put("username", username);
    params.put("password", Base64Util.encode(DigestUtil.md5(password)));
   /* HttpResponse response = HttpRequest.post("http://cas.whovc.edu.cn/sso/restlet/tickets").form(params).execute();
    String tgt = response.header("location");
    System.out.println("-------------------cas 服务器thg:"+tgt);
    int status = response.getStatus();
    if ((tgt != null) && (status == 201)) {
      tgt = tgt.substring(tgt.lastIndexOf("/") + 1);
      System.out.println("-------------------Demo中thg:"+tgt);
    }*/
    return "";
  }
}