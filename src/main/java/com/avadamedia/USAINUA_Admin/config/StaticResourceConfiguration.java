package com.avadamedia.USAINUA_Admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StaticResourceConfiguration implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/templates/**")
                .addResourceLocations("classpath:/templates/");
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:/home/avada/web/slj.avada-media-dev1.od.ua/slj/USAINUA/USAINUA_Admin/uploads/");
    }
}

