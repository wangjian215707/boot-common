package com.github.admin.edu.assembly.common.util;

import com.github.admin.edu.assembly.date.util.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.ParseException;
import java.util.Date;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-8-28
 */
public class HttpFileUploadUtil {

    private static final Logger logger= LoggerFactory.getLogger(HttpFileUploadUtil.class);

    /**
     * 文件上传，返回保存路径
     * @param inputStream
     * @param oldFileName 文件原名称
     * @return 保存后的路径 /upload/xxx
     */
    public static String uploadUtil(InputStream inputStream, String realPath, String oldFileName){
        return uploadUtil(inputStream,realPath,oldFileName,null);
    }

    /**
     * 文件上传，返回保存路径
     * @param inputStream
     * @param oldFileName 文件原名称
     * @param newFileName 保存后的文件名称
     * @return 保存后的路径 /upload/xxx
     */
    public static String uploadUtil(InputStream inputStream,String realPath,String oldFileName,String newFileName){
        if(null!=inputStream&&!StringUtils.isEmpty(oldFileName)&&!StringUtils.isEmpty(realPath)){
            BufferedInputStream buff = null;
            FileOutputStream fileOut = null;
            try {
                buff=new BufferedInputStream(inputStream);
                byte[] bytes = new byte[1024];
                int num = 0;
                String day=DateFormatUtils.formatDate(new Date(),"yyyy-MM-dd");
                oldFileName=oldFileName.substring(oldFileName.lastIndexOf("."),oldFileName.length());//获取后缀名
                if(StringUtils.isEmpty(newFileName)){
                    newFileName= DateFormatUtils.formatDate(new Date(),"yyyyMMddHHmmssSSS")+oldFileName;

                    //newFileName = oldFileName;//交院
                }
                String filePath=realPath+newFileName;
                File dest=new File(filePath);
                if(!dest.exists()){
                    dest.getParentFile().mkdirs();
                    dest.createNewFile();
                }
                fileOut = new FileOutputStream(dest);
                while ((num = buff.read(bytes)) != -1) {
                    fileOut.write(bytes, 0, num);
                }
                return newFileName;
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                logger.error("日期格式化异常！");
                e.printStackTrace();
            } finally {
                if (null != fileOut) {
                    try {
                        fileOut.flush();
                    } catch (IOException e) {
                        logger.error("文件流关闭出错:"+e.getMessage());
                    }
                    try {
                        fileOut.close();
                    } catch (IOException e) {
                        logger.error("文件流关闭出错:"+e.getMessage());
                    }
                }
                if (null != buff) {
                    try {
                        buff.close();
                    } catch (IOException e) {
                        logger.error("文件流关闭出错:"+e.getMessage());
                    }
                }
                if (null != inputStream) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        logger.error("没有获取到输入流："+e.getMessage());
                    }
                }
            }
        }
        return newFileName;
    }

}
