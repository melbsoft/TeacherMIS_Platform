package com.melbsoft.teacherplatform.config;

import io.undertow.UndertowOptions;
import org.springframework.boot.web.embedded.undertow.UndertowBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UndertowConfig {


    @Bean
    public UndertowBuilderCustomizer builderCustomizer() {
        return (builder) -> {
            builder.setServerOption(UndertowOptions.ENABLE_HTTP2, true)
                    .setServerOption(UndertowOptions.HTTP2_SETTINGS_ENABLE_PUSH, true);
        };
    }
}
