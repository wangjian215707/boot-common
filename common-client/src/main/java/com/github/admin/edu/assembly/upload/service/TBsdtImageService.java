package com.github.admin.edu.assembly.upload.service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * user:wangj
 * date:2018/11/25
 * Time: 0:49
 */
public interface TBsdtImageService {



    String uploadEditImage(MultipartHttpServletRequest request) ;

    String uploadEditFile(MultipartHttpServletRequest request);

}
