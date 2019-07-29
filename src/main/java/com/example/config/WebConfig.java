package com.example.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author HaN
 * @create 2019-04-16 19:50
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    //设置文件上传路径
    @Value(value = "${upload-modelImage-path}")
    private String modelImage;
    @Value(value = "${upload-brandIcon-path}")
    private String brandIcon;

    //对静态资源的配置
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            //http://localhost:9000/image/brand/28f4844ade0c4641ba9e282829a9b08f.png
            registry.addResourceHandler("/image/model/**")
                    .addResourceLocations("file:" + modelImage); //媒体资源
            registry.addResourceHandler("/image/brand/**")
                    .addResourceLocations("file:" + brandIcon);
        } else {  //linux 和mac
            registry.addResourceHandler("/image/model/**")
                    .addResourceLocations("file:D:/车辆图片/车辆型号图片/"); //媒体资源
            registry.addResourceHandler("/image/brand/**")
                    .addResourceLocations("file:D:/车辆图片/车辆品牌图标/");
        }
    }
}
