package com.melbsoft.teacherplatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.schema.Example;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Response;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableOpenApi
public class SwaggerConfig {

    List<Response> responses = new ArrayList<Response>() {{

        Example badRequestExample = new Example("无效请求", "无效请求", "请求内容无效时返回", "{\n" +
                "    \"code\": \"00001\",\n" +
                "    \"message\": \"INVALID REQUEST\"\n" +
                "}", "暂无", MediaType.APPLICATION_JSON_VALUE);
        Example unAuthorizeExample = new Example("无效请求", "无效请求", "请求内容无效时返回", "{\n" +
                "    \"code\": \"00002\",\n" +
                "    \"message\": \"UNAUTHORIZED\"\n" +
                "}", "暂无", MediaType.APPLICATION_JSON_VALUE);
        Example sysErrorExample = new Example("无效请求", "无效请求", "请求内容无效时返回", "{\n" +
                "    \"code\": \"00003\",\n" +
                "    \"message\": \"SYSTEM ERROR\"\n" +
                "}", "暂无", MediaType.APPLICATION_JSON_VALUE);
        add(new ResponseBuilder()
                .code("400")
                .description("无效请求")
                .examples(Collections
                        .singletonList(badRequestExample))
                .build());
        add(new ResponseBuilder()
                .code("401")
                .description("请求未授权")
                .examples(Collections
                        .singletonList(unAuthorizeExample))
                .build());
        add(new ResponseBuilder()
                .code("500")
                .description("服务器异常")
                .examples(Collections
                        .singletonList(sysErrorExample))
                .build());
    }};

    @Bean
    public Docket MultiPartAPI() {

        return new Docket(DocumentationType.OAS_30)
                .enable(true)
                .apiInfo(apiInfo())
                .produces(Collections.singleton(MediaType.APPLICATION_JSON_VALUE))
                .globalResponses(HttpMethod.GET, responses)
                .globalResponses(HttpMethod.POST, responses)
                .globalResponses(HttpMethod.PUT, responses)
                .globalResponses(HttpMethod.DELETE, responses)
                .globalRequestParameters(Collections
                        .singletonList(new RequestParameterBuilder()
                                .name("_csrf")
                                .build()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths((String input) -> {
                    return !input.matches("/error.*");
                })
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "MelbSoft Teacher MIS APIs",
                "backend service for Teacher MIS",
                "1.0.0",
                "https://melbsoft.com/terms",
                new Contact("melbsoft", "http://melbsoft.com", "support@melbsoft.team"),
                "License of API", "API license URL", Collections.emptyList());
    }
}
