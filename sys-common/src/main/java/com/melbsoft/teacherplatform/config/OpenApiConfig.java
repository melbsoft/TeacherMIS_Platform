package com.melbsoft.teacherplatform.config;

import com.melbsoft.teacherplatform.web.basic.ResultMessage;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.Parameter;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


    @Bean
    public OpenApiCustomiser apiCustomiser() {
        return api -> {
            api.getPaths().forEach(
                    (path, pathItem) -> pathItem.readOperations()
                            .forEach(o ->
                                    o.getResponses()
                                            .addApiResponse("400", new ApiResponse().$ref("400"))
                                            .addApiResponse("401", new ApiResponse().$ref("401"))
                                            .addApiResponse("403", new ApiResponse().$ref("403"))
                                            .addApiResponse("500", new ApiResponse().$ref("500"))
                            ));
            api.getPaths()
                    .addPathItem("/logout", new PathItem()
                            .post(new Operation()
                                    .operationId("logout")
                                    .summary("退出登陆")
                                    .addTagsItem("系统登录功能")
                                    .responses(new ApiResponses()
                                            .addApiResponse("200",
                                                    new ApiResponse()
                                                            .description("登陆成功")
                                                            .content(new Content()
                                                                    .addMediaType("application/json",
                                                                            new MediaType()
                                                                                    .schema(
                                                                                            new Schema<>()
                                                                                                    .addProperties("code", new StringSchema().example("00000"))
                                                                                                    .addProperties("message", new StringSchema().example("ok"))
                                                                                    )
                                                                    ))
                                            ))

                            ))
                    .addPathItem("/login", new PathItem()
                            .post(new Operation()
                                    .operationId("login")
                                    .summary("通过用户名密码登录系统")
                                    .addTagsItem("系统登录功能")
                                    .addParametersItem(new Parameter()
                                            .name("username")
                                            .in("query")
                                            .required(true)
                                            .description("登录用户名")
                                            .example("super")
                                    )
                                    .addParametersItem(new Parameter()
                                            .name("password")
                                            .in("query")
                                            .required(true)
                                            .description("登录密码")
                                            .example("Pass_1234")
                                            .schema(new PasswordSchema())
                                    )
                                    .addParametersItem(new Parameter()
                                            .name("remember-me")
                                            .in("query")
                                            .description("是否记住本次登陆")
                                            .example("true")
                                            .schema(new BooleanSchema())
                                    )
                                    .responses(new ApiResponses()
                                            .addApiResponse("200",
                                                    new ApiResponse()
                                                            .description("登陆成功")
                                                            .content(new Content()
                                                                    .addMediaType("application/json",
                                                                            new MediaType()
                                                                                    .example("{\n" +
                                                                                            "    \"code\": \"00000\",\n" +
                                                                                            "    \"message\": \"ok\",\n" +
                                                                                            "    \"data\": {\n" +
                                                                                            "        \"details\": {\n" +
                                                                                            "            \"remoteAddress\": \"0:0:0:0:0:0:0:1\",\n" +
                                                                                            "            \"sessionId\": \"2e105d86-9980-4da6-88b9-7be751b9c571\"\n" +
                                                                                            "        },\n" +
                                                                                            "        \"authenticated\": true,\n" +
                                                                                            "        \"principal\": {\n" +
                                                                                            "            \"username\": \"leo\",\n" +
                                                                                            "            \"accountNonExpired\": true,\n" +
                                                                                            "            \"accountNonLocked\": true,\n" +
                                                                                            "            \"credentialsNonExpired\": true,\n" +
                                                                                            "            \"enabled\": true\n" +
                                                                                            "        },\n" +
                                                                                            "        \"name\": \"leo\"\n" +
                                                                                            "    }\n" +
                                                                                            "}")
                                                                    ))
                                            ))

                            )

                    );
        };
    }

    @Bean
    public OpenAPI springdocAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addResponses("400", new ApiResponse()
                                .description("无效请求")
                                .content(new Content()
                                        .addMediaType("application/problem+json", new MediaType()
                                                .schema(
                                                        new Schema<>()
                                                                .addProperties("code", new StringSchema().example("00001"))
                                                                .addProperties("message", new StringSchema().example("INVALID REQUEST"))
                                                ))))
                        .addResponses("401", new ApiResponse()
                                .description("请求未授权")
                                .content(new Content()
                                        .addMediaType("application/problem+json", new MediaType()
                                                .schema(
                                                        new Schema<ResultMessage<Void>>()
                                                                .addProperties("code", new StringSchema().example("00002"))
                                                                .addProperties("message", new StringSchema().example("UNAUTHORIZED"))
                                                ))))
                        .addResponses("403", new ApiResponse()
                                .description("操作无法完成")
                                .content(new Content()
                                        .addMediaType("application/problem+json", new MediaType()
                                                .schema(
                                                        new Schema<ResultMessage<Void>>()
                                                                .addProperties("code", new StringSchema().example("00003"))
                                                                .addProperties("message", new StringSchema().example("自定义用户消息"))
                                                ))))
                        .addResponses("500", new ApiResponse()
                                .description("服务器异常")
                                .content(new Content()
                                        .addMediaType("application/problem+json", new MediaType()
                                                .schema(
                                                        new Schema<ResultMessage<Void>>()
                                                                .addProperties("code", new StringSchema().example("00010"))
                                                                .addProperties("message", new StringSchema().example("SYSTEM ERROR"))
                                                ))))
                )
                .info(
                        new Info()
                                .title("MelbSoft Teacher MIS APIs")
                                .description("backend service for Teacher MIS")
                                .version("v0.0.1")
                                .license(new License().name("Apache 2.0")
                                        .url("https://melbsoft.com/terms"))
                                .contact(new Contact()
                                        .email("support@melbsoft.team")
                                        .name("melbsoft")
                                        .url("http://melbsoft.com")

                                )
                );
    }


}
