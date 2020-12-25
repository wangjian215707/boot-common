package com.github.edu.client.common.configuration;

import com.github.edu.client.common.interceptor.ErrorInterceptor;
import com.github.edu.client.common.interceptor.UserInformationInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

/**
 * Create by IntelliJ IDEA
 * 用户：王建
 * 日期：2018-7-24
 */
@Configuration
public class ClientConfiguration implements WebMvcConfigurer {

    @Value("${server.custom.interceptor.user.path}")
    private String[] userInterceptorPath;

    @Value("${server.custom.interceptor.user.exclude-path}")
    private String[] userExcludePath;

    @Value("${server.custom.static.file.upload-path}")
    private String filePath;

    @Value("${server.custom.static.file.url}")
    private String[] fileUrl;

    @Value("${server.custom.static.image.upload-path}")
    private String imagePath;

    @Value("${server.custom.static.image.url}")
    private String[] imageUrl;

//    交院使用
  /*  @Value("${server.custom.static.image.sfzzm-path}")
    private String sfzzmPath;

    @Value("${server.custom.static.image.sfzfm-path}")
    private String sfzfmPath;
    @Value("${server.custom.static.image.serviceimage-path}")
    private String serviceimagepath;
    @Value("${server.custom.static.image.sfzzmurl}")
    private String[] sfzzmurl;
    @Value("${server.custom.static.image.sfzfmurl}")
    private String[] sfzfmurl;
    @Value("${server.custom.static.image.serviceimageurl}")
    private String[] serviceimageurl;*/



    /**
     * 配置静态地址（不执行任何逻辑操作）
     * @param registry
     */
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/login").setViewName("index/login/index");
        //registry.addViewController("/error").setViewName("common/error");
        registry.addViewController("/index").setViewName("index/index");
    }

    /**
     * 跨域权限设置
     * @param registry
     */
    public  void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**");//所有请求，都支持跨域

        /*registry.addMapping("/api/**").allowedOrigins("http://www.baidu.com").allowedMethods("POST","GET");*/
    }


    /**
     * 注册拦截器
     * @param registry
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations(ResourceUtils.CLASSPATH_URL_PREFIX+"/static/");
        //registry.addResourceHandler("/files/**").addResourceLocations("file:///var/spring/uploaded_files");
        //特别注意  在win环境下 路径最后一定要加“/”
        //设置图标保存路径
        registry.addResourceHandler(imageUrl).addResourceLocations("file:///"+imagePath);
        //设置文件保存路径
        registry.addResourceHandler(fileUrl).addResourceLocations("file:///"+filePath);

    }

    public void addInterceptors(InterceptorRegistry registry){

        /**
         * 异常展示页面
         */
        registry.addInterceptor(getErrorInterceptor())
                .addPathPatterns("/**");
        /***
         * 设置用户基本信息
         */
        registry.addInterceptor(getUserInformationInterceptor()).
                addPathPatterns(userInterceptorPath).
                excludePathPatterns(userExcludePath);
    }

    @Bean
    public UserInformationInterceptor getUserInformationInterceptor(){
        return new UserInformationInterceptor();
    }

    @Bean
    public ErrorInterceptor getErrorInterceptor(){
        return new ErrorInterceptor();
    }


}
