package com.github.admin.edu.assembly.upload.controller;

import com.github.admin.edu.assembly.upload.service.TBsdtImageService;
import com.github.edu.client.common.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2019-4-25
 */
@RestController
@RequestMapping("/s/ajax/upload")
public class UploadFileController extends BaseController {

    @Autowired
    private TBsdtImageService service;

    @PostMapping("/edit/image")
    public String editIndex(MultipartHttpServletRequest request){
        return service.uploadEditImage(request);
    }

    @PostMapping("/edit/file")
    public String editFile(MultipartHttpServletRequest request){
        return service.uploadEditFile(request);
    }
}
