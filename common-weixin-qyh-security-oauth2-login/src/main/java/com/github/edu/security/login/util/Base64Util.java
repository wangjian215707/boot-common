package com.github.edu.security.login.util;

import java.io.UnsupportedEncodingException;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Base64Util
{
  public static String encode(String s)
  {
    if (s == null)
      return null;
    String res = "";
    try {
      res = new BASE64Encoder().encode(s.getBytes("utf-8"));
    }
    catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
    return res;
  }

  public static String decode(String s)
  {
    if (s == null)
      return null;
    BASE64Decoder decoder = new BASE64Decoder();
    try {
      byte[] b = decoder.decodeBuffer(s);
      return new String(b, "utf-8"); } catch (Exception e) {
    }
    return null;
  }
}