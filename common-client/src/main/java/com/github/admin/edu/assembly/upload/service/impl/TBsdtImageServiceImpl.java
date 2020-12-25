package com.github.admin.edu.assembly.upload.service.impl;

import com.github.admin.edu.assembly.common.entity.LayerUploadMsg;
import com.github.admin.edu.assembly.common.util.HttpFileUploadUtil;
import com.github.admin.edu.assembly.common.util.JsonUtils;
import com.github.admin.edu.assembly.upload.service.TBsdtImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.Arrays;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-4-25
 */
@Service
@Slf4j
public class TBsdtImageServiceImpl implements TBsdtImageService {

    @Value("${server.custom.static.image.upload-path}")
    private String uploadPath;
    @Value("${server.custom.static.image.type}")
    private String[] imageTypes;
    @Value("${server.custom.static.file.upload-path}")
    private String fileUploadPath;
    @Value("${server.custom.static.type}")
    private String[] fileTypes;
    @Value("${server.custom.cas.client.host}")
    private String host;

    @Override
    public String uploadEditImage(MultipartHttpServletRequest request) {
        LayerUploadMsg message = new LayerUploadMsg();
        MultipartFile multipartFile=request.getFile("imgFile");
        if(null==multipartFile){
            multipartFile=request.getFile("file");
        }
        String oldFileName=multipartFile.getOriginalFilename();
        String fileExt = oldFileName.substring(oldFileName.lastIndexOf(".") + 1).toLowerCase();
        if (!Arrays.asList(fileTypes).contains(fileExt)){//判断图片后缀名
            message.setMsg("a上传失败！文件名无效！");
            message.setCode(1);
            return JsonUtils.toJson(message);
        }
        try {
            String newFileName= HttpFileUploadUtil.uploadUtil(multipartFile.getInputStream(),uploadPath,oldFileName);
            message.setCode(0);
            message.setUrl(host+"/s/static/image/"+newFileName);
            return JsonUtils.toJson(message);
        }catch (Exception e){
            log.error(e.getMessage());
            message.setMsg(e.getMessage());
        }
        message.setCode(1);
        return JsonUtils.toJson(message);
    }

    @Override
    public String uploadEditFile(MultipartHttpServletRequest request) {
        LayerUploadMsg message = new LayerUploadMsg();
        MultipartFile multipartFile=request.getFile("file");
        String oldFileName=multipartFile.getOriginalFilename();
        String fileExt = oldFileName.substring(oldFileName.lastIndexOf(".") + 1).toLowerCase();
        if (!Arrays.asList(fileTypes).contains(fileExt)){//判断图片后缀名
            message.setMsg("上传失败！文件名无效！");
            message.setCode(1);
            return JsonUtils.toJson(message);
        }
        try {
            String newFileName= HttpFileUploadUtil.uploadUtil(multipartFile.getInputStream(),fileUploadPath,oldFileName);
            message.setCode(0);
            message.setUrl("/s/static/file/"+newFileName);
            message.setNewFileName(newFileName);
            message.setOldFileName(oldFileName);
            return JsonUtils.toJson(message);
        }catch (Exception e){
            log.error(e.getMessage());
            message.setMsg(e.getMessage());
        }
        message.setCode(1);
        return JsonUtils.toJson(message);
    }

}
