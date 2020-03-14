package com.chunqiu.mrjuly.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 静态资源访问路径配置
 * 增加上传图片的路径访问
 */
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String os = System.getProperty("os.name");
        if(os.toLowerCase().contains("windows")){
            registry.addResourceHandler("/**")
                    .addResourceLocations("file:D:/uploadimages")
                    .addResourceLocations("classpath:/resources/")
                    .addResourceLocations("classpath:/static/")
                    .addResourceLocations("classpath:/public/")
                    .addResourceLocations("file:D:/data/uploadfiles")
                    .addResourceLocations("file:D:/uploadfile");
        }else{
            registry.addResourceHandler("/**")
                    .addResourceLocations("file:/data/")
                    .addResourceLocations("classpath:/resources/")
                    .addResourceLocations("classpath:/static/")
                    .addResourceLocations("classpath:/public/");
        }
    }
}
