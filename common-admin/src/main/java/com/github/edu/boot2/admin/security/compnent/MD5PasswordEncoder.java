package com.github.edu.boot2.admin.security.compnent;

import org.springframework.security.crypto.codec.Hex;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * 自定义MD5加密方式
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2020/4/24
 */
public class MD5PasswordEncoder implements PasswordEncoder {
    /**
     * Encode the raw password. Generally, a good encoding algorithm applies a SHA-1 or
     * greater hash combined with an 8-byte or greater randomly generated salt.
     *
     * @param rawPassword
     */
    @Override
    public String encode(CharSequence rawPassword) {
        Random random=new Random();
        StringBuilder builder=new StringBuilder(16);
        builder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int length=builder.length();
        if(length<16){
            for (int i=0;i<16-length;i++){
                builder.append("0");
            }
        }
        String slat=builder.toString();
        rawPassword =md5Hex(rawPassword+slat);
        char[] cs=new char[48];
        for (int i=0;i<48;i+=3){
            System.out.println(i/3*2);
            cs[i]=rawPassword.charAt(i/3*2);
            char c=slat.charAt(i/3);
            cs[i+1]=c;
            cs[i+2]=rawPassword.charAt(i/3*2+1);
        }
        return String.valueOf(cs);
    }

    /**
     * Verify the encoded password obtained from storage matches the submitted raw
     * password after it too is encoded. Returns true if the passwords match, false if
     * they do not. The stored password itself is never decoded.
     *
     * @param rawPassword     the raw password to encode and match
     * @param encodedPassword the encoded password from storage to compare with
     * @return true if the raw password, after encoding, matches the encoded password from
     * storage
     */
    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.out.println("rawPassword:"+rawPassword);
        char[] cs1=new char[32];
        char[] cs2=new char[16];
        for (int i=0;i<48;i+=3){
            cs1[i/3*2]=encodedPassword.charAt(i);
            cs1[i/3*2+1]=encodedPassword.charAt(i+2);
            cs2[i/3]=encodedPassword.charAt(i+1);
        }
        String slat=new String(cs2);
        return md5Hex(rawPassword+slat).equals(String.valueOf(cs1));
    }

    private static String md5Hex(String str){
        try {
            MessageDigest messageDigest=MessageDigest.getInstance("MD5");
            byte[] digest=messageDigest.digest(str.getBytes());
            return new String(new Hex().encode(digest));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
