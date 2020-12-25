package com.github.admin.edu.assembly.string.util;


import com.google.common.base.Splitter;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-12
 */
public class StringUtils {

    public static final String STRING="String";

    public static final String LONG="Long";

    public static final String INTEGER="Integer";

    public static boolean isBlank(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(str.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str  the String to check, may be null
     * @return <code>true</code> if the String is
     *  not empty and not null and not whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    public static List getIterable(String type, String ids){
        List list=new ArrayList();
        if(!org.springframework.util.StringUtils.isEmpty(ids)){
            Iterable<String> iterable= Splitter.on(",").trimResults().split(ids);
            for (String string:iterable){
                if(STRING.equals(type)){
                    list.add(string);
                }else if (LONG.equals(type)){
                    list.add(Long.parseLong(string));
                }else if(INTEGER.equals(type)){
                    list.add(Integer.parseInt(string));
                }
            }

        }
        return list;
    }

    public static String[] getString(String str){
        if(!org.springframework.util.StringUtils.isEmpty(str)){
            return str.split(",");
        }
        return null;
    }

    /**
     * @Description: 整数相除结果转换成指定位数的百分数
     * @param dividend : 被除数(分子)
     * @param divisor : 除数(分母)
     * @param digit : 保留几位小数
     * @return String
     */
    public static String getPercent(int dividend,int divisor,int digit) {
        return getPercent((float) dividend,(float)divisor,digit);
    }

    /**
     * @Description: 整数相除结果转换成指定位数的百分数
     * @param dividend : 被除数(分子)
     * @param divisor : 除数(分母)
     * @param digit : 保留几位小数
     * @return String
     */
    public static String getPercent(Long dividend,Long divisor,int digit) {
        return getPercent((float) dividend,(float)divisor,digit);
    }

    private static  String  getPercent(float dividend,float divisor,int digit){
        Float result = dividend/divisor;
        if (result.isNaN()) {
            return "";
        }else {
            //获取格式化对象
            NumberFormat nt = NumberFormat.getPercentInstance();
            //设置百分数精确度,即保留几位小数
            nt.setMinimumFractionDigits(digit);
            String finalResult = nt.format(result);
            return finalResult;
        }
    }
}
