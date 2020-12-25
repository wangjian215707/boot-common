package com.github.admin.edu.assembly.date.util;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期格式化，SimpleDateFormat
 * 线程不安全的，如果使用过程中，不断new SimpleDateFormat,
 * 将会大量占用jvm和内存空间。
 * 1.自己写公用类的时候，要对多线程调用情况下的后果在注释里进行明确说明
 * 2.对线程环境下，对每一个共享的可变变量都要注意其线程安全性
 * 3.我们的类和方法在做设计的时候，要尽量设计成无状态的
 * 使用ThreadLocal,将共享变量变为独享，线程独享肯定能比方法独享在并发环境中能减少不少创建对象的开销。
 * Created by 王建 on 2017/6/13.
 */
public class DateFormatUtils {
    private static final String date_format = "yyyy/MM/dd HH:mm:ss";

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<DateFormat>();

    /**
     * @param format 格式化样式
     * @return
     */
    public static DateFormat getDateFormat(String format) {
        DateFormat df = threadLocal.get();
        if (df == null && StringUtils.isEmpty(format)) {
            df = new SimpleDateFormat(date_format);
            threadLocal.set(df);
        } else {
            df = new SimpleDateFormat(format);
            threadLocal.set(df);
        }
        return df;
    }

    /**
     * 日期格式化为字符串，采用默认格式
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formatDate(Date date) throws ParseException {
        return getDateFormat("yyyy/MM/dd").format(date);
    }

    /**
     * 日期格式化为字符串，采用自定义格式
     *
     * @param date
     * @param format
     * @return
     * @throws ParseException
     */
    public static String formatDate(Date date, String format) throws ParseException {
        return getDateFormat(format).format(date);
    }

    /**
     * 字符串格式化为日期，采用默认格式
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date parse(String strDate) throws ParseException {
        return getDateFormat("yyyy/MM/dd").parse(strDate);
    }

    public static Date parse(String strDate, String format) throws ParseException {
        return getDateFormat(format).parse(strDate);
    }

    /**
     * 比较日期是否大于指定日期
     *
     * @param startDate
     * @param thanDate
     * @param type
     * @return
     */
    public static boolean dateCompareGreaterThan(String startDate, String thanDate, String type) throws ParseException {
        Date start = parse(startDate, type);
        Date than = parse(thanDate, type);
        if (than.getTime() > start.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 比较日期是否小于指定日期
     *
     * @param endDate
     * @param thanDate
     * @param type
     * @return
     * @throws ParseException
     */
    public static boolean dateCompareLessThan(String endDate, String thanDate, String type) throws ParseException {
        Date end = parse(endDate, type);
        Date than = parse(thanDate);
        if (than.getTime() < end.getTime()) {
            return true;
        }
        return false;
    }

    /**
     * 计算两个时间的日期差
     *
     * @param minDate
     * @param maxDate
     * @return
     */
    public static int daysBetween(String minDate, String maxDate) throws ParseException {
        DateFormat sdf = getDateFormat("yyyy/MM/dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        cal.setTime(sdf.parse(minDate));
        time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(maxDate));
        time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 计算两个时间相差的小时数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int timeBetween(String startTime, String endTime) throws ParseException {
        DateFormat sdf = getDateFormat("yyyy/MM/dd HH");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        cal.setTime(sdf.parse(startTime));
        time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(endTime));
        time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600);
        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 得到几天后的时间
     * @param type 日期格式化样式
     * @param startDate 开始日期
     * @param day 几天后
     * @return
     */
    public static String getDateAfter(String type,String startDate, int day) throws ParseException {
        DateFormat sdf = getDateFormat(type);
        Calendar now = Calendar.getInstance();
        now.setTime(sdf.parse(startDate));
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return sdf.format(now.getTime());
    }

    /**
     * 计算几分钟后的时间
     * @param type
     * @param startDateTime
     * @param time
     * @return
     * @throws ParseException
     */
    public static String getDateMinuteAfter(String type,String startDateTime, int time) throws ParseException {
        DateFormat sdf = getDateFormat(type);
        Calendar now = Calendar.getInstance();
        now.setTime(sdf.parse(startDateTime));
        now.add(Calendar.MINUTE, time);// 24小时制
        //得到结算后的结果 yyyy-MM-dd HH:mm
        return sdf.format(now.getTime());
    }

    public static String getDateHourAfter(String type,String startDateTime, int hour) throws ParseException {
        DateFormat sdf = getDateFormat(type);
        Calendar now = Calendar.getInstance();
        now.setTime(sdf.parse(startDateTime));
        now.add(Calendar.HOUR, hour);// 24小时制
        //得到结算后的结果 yyyy-MM-dd HH:mm
        return sdf.format(now.getTime());
    }
    public static int dayForWeek(String type,String pTime) throws Exception {
        DateFormat sdf = getDateFormat(type);
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(pTime));
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }


}
