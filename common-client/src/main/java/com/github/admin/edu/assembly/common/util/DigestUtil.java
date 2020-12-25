package com.github.admin.edu.assembly.common.util;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * 【类名】：文件摘要工具<br/>
 * 【简介】：<br/>
 * 目前已实现以下摘要：<br/>
 * 1、MD5<br/>
 * 2、SHA1<br/>
 * 【依赖】：<br/>
 *
 * @author SwordRays
 * @date 2013年7月20日 下午3:28:01
 * @since JDK1.
 * * @version 1.0.0
 */
public class DigestUtil {
    /** ========================== 属性-常量 ================================== */
    /**
     * 摘要-文件读取模式-流
     */
    public static final Integer DIGEST_FILE_READ_MODE_STREAM = 1000 + 1;
    /**
     * 摘要-文件读取模式-随机读取
     */
    public static final Integer DIGEST_FILE_READ_MODE_RANDOMACCESSFILE = 1000 + 2;
    /**
     * 摘要-文件读取模式-文件管道-缓冲
     */
    public static final Integer DIGEST_FILE_READ_MODE_FILECHANNEL_BUFFER = 1000 + 3;
    /**
     * 摘要-文件读取模式-文件管道-传输
     */
    public static final Integer DIGEST_FILE_READ_MODE_FILECHANNEL_TRANSFER = 1000 + 4;
    /**
     * 摘要-文件读取模式-文件管道-内存映射
     */
    public static final Integer DIGEST_FILE_READ_MODE_FILECHANNEL_MAPPED = 1000 + 5;
    /**
     * 摘要-文件读取模式-nio的Files工具类
     */
    public static final Integer DIGEST_FILE_READ_MODE_FILES = 1000 + 6;

    /** ========================== 属性-变量 ================================== */

    /**
     * ========================== 方法-构造 ==================================
     */
    private DigestUtil() {
    }

    /** ========================== 方法-私有 ================================== */

    /** ========================== 方法-保护 ================================== */

    /** ========================== 方法-公开 ================================== */
    /** ========================== 摘要 ================================== */
    /**
     * 【方法名】：对字符串进行摘要<br/>
     * 【简介】：<br/>
     * 【创建时间】：2013年7月20日 下午4:13:11<br/>
     *
     * @param data         待处理数据
     * @param digestScheme 摘要模式
     * @param isUpper      是否将摘要结果转换为大写字符串[false]
     * @return 摘要字符串
     */
    protected static String digest(String data, String digestScheme, Boolean isUpper) {
        String result = null;
        if (null != data) {
            isUpper = null == isUpper ? false : isUpper;
            try {
                //获取输入字节数组
                byte[] inputStrBytes = data.getBytes();
                // 获得摘要算法的对象
                MessageDigest messageDigest = MessageDigest.getInstance(digestScheme);
                // 使用指定的字节更新摘要（可以累积多次更新）
                messageDigest.update(inputStrBytes);
                // 获得摘要数组
                byte[] digestBytes = messageDigest.digest();
                //把摘要数组转换成十六进制的字符串
                result = toHexString(digestBytes, isUpper);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * @see #digest(String, String, Boolean)
     */
    protected static String digest(String data, String digestScheme) {
        return digest(data, digestScheme, false);
    }

    /**
     * 【方法名】：对文件进行摘要<br/>
     * 【简介】：<br/>
     * 【创建时间】：2013年7月20日 下午4:20:11<br/>
     *
     * @param file         文件
     * @param digestScheme 摘要模式
     * @param fileReadMode 文件读取模式[{@link #DIGEST_FILE_READ_MODE_STREAM}]
     * @param isUpper      是否将摘要结果转换为大写字符串[false]
     * @return
     */
    protected static String digest(File file, String digestScheme, Integer fileReadMode, Boolean isUpper) {
        String result = null;
        if (null != file) {
            fileReadMode = null == fileReadMode ? DIGEST_FILE_READ_MODE_STREAM : fileReadMode;
            isUpper = null == isUpper ? false : isUpper;

            FileInputStream fis = null;
            BufferedInputStream bis = null;
            FileChannel fc = null;
            try {
                MessageDigest messageDigest = MessageDigest.getInstance(digestScheme);
                if (DIGEST_FILE_READ_MODE_STREAM.equals(fileReadMode)) {
                    /* 字节流方式，适合计算后需要删除文件的场景 */
                    bis = new BufferedInputStream(new FileInputStream(file));
                    byte[] buffer = new byte[1024 * 1024];
                    int numRead = 0;
                    while ((numRead = bis.read(buffer)) > 0) {
                        messageDigest.update(buffer, 0, numRead);
                    }
                } else if (DIGEST_FILE_READ_MODE_FILECHANNEL_MAPPED.equals(fileReadMode)) {
                    /* 内存隐射方式，适合超大文件。但是计算完之后无法立即删除文件 */
                    fis = new FileInputStream(file);
                    fc = fis.getChannel();
                    MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
                    messageDigest.update(byteBuffer);
                } else {
                    System.out.println("未识别的摘要方案！");
                }

                // 获得摘要数组
                byte[] digestBytes = messageDigest.digest();
                //把摘要数组转换成十六进制的字符串
                result = toHexString(digestBytes, isUpper);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (null != fc) {
                        fc.close();
                    }
                    if (null != bis) {
                        bis.close();
                    }
                    if (null != fis) {
                        fis.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * 【方法名】：将字节数组转换成十六进制字符串<br/>
     * 【简介】：<br/>
     * 【创建时间】：2013年7月20日 下午3:34:29<br/>
     *
     * @param bytes   需要转换的字节数组
     * @param isUpper 是否返回大写的十六进制字符串[false]
     * @return 十六进制字符串
     */
    public static String toHexString(byte[] bytes, boolean isUpper) {
        String result = null;
        if (null != bytes && bytes.length > 0) {

            StringBuffer sb = new StringBuffer(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                String hex = Integer.toHexString(bytes[i] & 0xFF);
                if (hex.length() == 1) {
                    hex = '0' + hex;
                }
                sb.append(hex);
            }
            if (isUpper) {
                result = sb.toString().toUpperCase();
            } else {
                result = sb.toString();
            }
        }
        return result;
    }

    /**
     * @see #digest(File, String, Integer, Boolean)
     */
    protected static String digest(File file, String digestScheme, Integer fileReadMode) {
        return digest(file, digestScheme, fileReadMode, false);
    }

    /**
     * @see #digest(File, String, Integer, Boolean)
     */
    protected static String digest(File file, String digestScheme) {
        return digest(file, digestScheme, DIGEST_FILE_READ_MODE_STREAM, false);
    }

    /** ========================== 摘要-MD5 ================================== */
    /**
     * 【方法名】：对字符串进行摘要-MD5方案<br/>
     * 【简介】：<br/>
     * 【创建时间】：2013年7月20日 下午4:45:12<br/>
     *
     * @param data    待处理数据
     * @param isUpper 结果是否转换为大写[false]
     * @return 摘要后的十六进制字符串
     */
    public static String md5(String data, Boolean isUpper) {
        //Double startTime = DateTimeUtil.getSecond();
        String result = null;
        isUpper = null == isUpper ? false : isUpper;
        result = digest(data, "MD5", isUpper);
        //InnerLogUtil.debug("本次操作时间：" + DateTimeUtil.cutDecimal(DateTimeUtil.calcUsedSecond(startTime), 3) + "s");
        return result;
    }

    /**
     * @see #md5(String, Boolean)
     */
    public static String md5(String data) {
        return md5(data, false);
    }

    /**
     * 【方法名】：对文件进行摘要-MD5方案<br/>
     * 【简介】：<br/>
     * 【创建时间】：2013年7月20日 下午4:50:07<br/>
     *
     * @param file         待处理文件
     * @param fileReadMode 文件读取模式[{@link #DIGEST_FILE_READ_MODE_STREAM}]
     * @param isUpper      结果是否转换为大写[false]
     * @return 摘要后的十六进制字符串
     */
    public static String md5(File file, Integer fileReadMode, Boolean isUpper) {
        //Double startTime = DateTimeUtil.getSecond();
        String result = null;
        fileReadMode = null == fileReadMode ? DIGEST_FILE_READ_MODE_STREAM : fileReadMode;
        isUpper = null == isUpper ? false : isUpper;
        result = digest(file, "MD5", fileReadMode, isUpper);
        //InnerLogUtil.debug("本次操作时间：" + DateTimeUtil.cutDecimal(DateTimeUtil.calcUsedSecond(startTime), 3) + "s");
        return result;
    }

    /**
     * @see #md5(File, Integer, Boolean)
     */
    public static String md5(File file, Integer fileReadMode) {
        return md5(file, fileReadMode, false);
    }

    /**
     * @see #md5(File, Integer, Boolean)
     */
    public static String md5(File file) {
        return md5(file, DIGEST_FILE_READ_MODE_STREAM, false);
    }

    /** ========================== 摘要-SHA1 ================================== */
    /**
     * 【方法名】：对字符串进行摘要-SHA1方案<br/>
     * 【简介】：<br/>
     * 【创建时间】：2013年7月20日 下午4:45:12<br/>
     *
     * @param data    待处理数据
     * @param isUpper 结果是否转换为大写[false]
     * @return 摘要后的十六进制字符串
     */
    public static String sha1(String data, Boolean isUpper) {
        //Double startTime = DateTimeUtil.getSecond();
        String result = null;
        isUpper = null == isUpper ? false : isUpper;
        result = digest(data, "SHA1", isUpper);
        //InnerLogUtil.debug("本次操作时间：" + DateTimeUtil.cutDecimal(DateTimeUtil.calcUsedSecond(startTime), 3) + "s");
        return result;
    }

    /**
     * @see #sha1(String, Boolean)
     */
    public static String sha1(String data) {
        return sha1(data, false);
    }

    /**
     * 【方法名】：对文件进行摘要-SHA1方案<br/>
     * 【简介】：<br/>
     * 【创建时间】：2013年7月20日 下午4:50:07<br/>
     *
     * @param file         待处理文件
     * @param fileReadMode 文件读取模式[{@link #DIGEST_FILE_READ_MODE_STREAM}]
     * @param isUpper      结果是否转换为大写[false]
     * @return 摘要后的十六进制字符串
     */
    public static String sha1(File file, Integer fileReadMode, Boolean isUpper) {
        //Double startTime = DateTimeUtil.getSecond();
        String result = null;
        fileReadMode = null == fileReadMode ? DIGEST_FILE_READ_MODE_STREAM : fileReadMode;
        isUpper = null == isUpper ? false : isUpper;
        result = digest(file, "SHA1", fileReadMode, isUpper);
        //InnerLogUtil.debug("本次操作时间：" + DateTimeUtil.cutDecimal(DateTimeUtil.calcUsedSecond(startTime), 3) + "s");
        return result;
    }

    /**
     * @see #sha1(File, Integer, Boolean)
     */
    public static String sha1(File file, Integer fileReadMode) {
        return sha1(file, fileReadMode, false);
    }

    /**
     * @see #sha1(File, Integer, Boolean)
     */
    public static String sha1(File file) {
        return sha1(file, DIGEST_FILE_READ_MODE_STREAM, false);
    }
}

