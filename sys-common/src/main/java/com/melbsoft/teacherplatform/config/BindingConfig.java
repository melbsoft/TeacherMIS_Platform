package com.melbsoft.teacherplatform.config;


import com.melbsoft.teacherplatform.component.LocalDateTimeConverter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class BindingConfig implements WebMvcConfigurer {

    @Resource
    LocalDateTimeConverter localDateTimeConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(localDateTimeConverter);
    }
}
