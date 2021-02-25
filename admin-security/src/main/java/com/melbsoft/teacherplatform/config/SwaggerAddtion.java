package com.melbsoft.teacherplatform.config;

import com.google.common.collect.Sets;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ExampleBuilder;
import springfox.documentation.builders.OperationBuilder;
import springfox.documentation.builders.RequestParameterBuilder;
import springfox.documentation.builders.ResponseBuilder;
import springfox.documentation.schema.Example;
import springfox.documentation.schema.ScalarType;
import springfox.documentation.service.ApiDescription;
import springfox.documentation.service.Operation;
import springfox.documentation.service.ParameterType;
import springfox.documentation.service.RequestParameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.ApiListingScannerPlugin;
import springfox.documentation.spi.service.contexts.DocumentationContext;
import springfox.documentation.spring.web.readers.operation.CachingOperationNameGenerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class SwaggerAddtion implements ApiListingScannerPlugin {

    @Override
    public List<ApiDescription> apply(DocumentationContext context) {

        RequestParameter userNameParameter = new RequestParameterBuilder()
                .name("username")
                .description("登录用户名")
                .in(ParameterType.QUERY)
                .query(query -> {
                    query.model(model -> {
                        model.scalarModel(ScalarType.STRING);
                    });
                })
                .required(true)
                .parameterIndex(0)
                .build();
        RequestParameter passwordParameter = new RequestParameterBuilder()
                .name("password")
                .description("登录密码")
                .in(ParameterType.QUERY)
                .query(query -> {
                    query.model(model -> {
                        model.scalarModel(ScalarType.PASSWORD);
                    });
                })
                .required(true)
                .parameterIndex(1)
                .build();
        RequestParameter csrfParameter = new RequestParameterBuilder()
                .name("_csrf")
                .description("安全随机数")
                .in(ParameterType.QUERY)
                .query(query -> {
                    query.model(model -> {
                        model.scalarModel(ScalarType.STRING);
                    });
                })
                .required(true)
                .parameterIndex(2)
                .build();
        RequestParameter rememberMeParameter = new RequestParameterBuilder()
                .name("remember-me")
                .description("是否免登录")
                .in(ParameterType.QUERY)
                .query(query -> {
                    query.model(model -> {
                        model.scalarModel(ScalarType.STRING);
                    });
                })
                .parameterIndex(3)
                .build();
        Example responseExample = new ExampleBuilder()
                .id("2")
                .summary("授权信息")
                .mediaType(MediaType.APPLICATION_JSON_VALUE)
                .value("{\n" +
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
                .build();
        Operation loginOperation =
                new OperationBuilder(new CachingOperationNameGenerator())
                        .method(HttpMethod.POST)
                        .summary("用户登录")
                        .notes("登录接口，通过用户名密码获取登录权限")
                        .consumes(Sets.newHashSet(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                        .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE))
                        .tags(Sets.newHashSet("系统登录功能"))
                        .requestParameters(Arrays.asList(
                                userNameParameter,
                                passwordParameter,
                                csrfParameter,
                                rememberMeParameter
                        ))
                        .responses(Collections.singletonList(
                                new ResponseBuilder()
                                        .code("200")
                                        .description("ok")
                                        .isDefault(true)
                                        .examples(Collections.singletonList(
                                                responseExample))
                                        .build()
                        ))

                        .build();

        ApiDescription loginApi =
                new ApiDescription("系统登录功能", "/login", "登录接口", "",
                        Collections.singletonList(loginOperation), false);


        Operation logoutOperation =
                new OperationBuilder(new CachingOperationNameGenerator())
                        .method(HttpMethod.POST)
                        .summary("用户登出")
                        .notes("登出接口，退出当前登录功能")
                        .consumes(Sets.newHashSet(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                        .produces(Sets.newHashSet(MediaType.APPLICATION_JSON_VALUE))
                        .tags(Sets.newHashSet("系统登录功能"))
                        .requestParameters(Arrays.asList(
                                csrfParameter
                        ))
                        .responses(Collections.singletonList(
                                new ResponseBuilder()
                                        .code("200")
                                        .description("ok")
                                        .isDefault(true)
                                        .examples(Collections.singletonList(responseExample))
                                        .build()
                        ))
                        .build();

        ApiDescription logoutApi =
                new ApiDescription("系统登录功能", "/logout", "登出接口", "",
                        Collections.singletonList(logoutOperation), false);
        return Arrays.asList(loginApi, logoutApi);
    }

    /**
     * 是否使用此插件
     *
     * @param documentationType swagger文档类型
     * @return true 启用
     */
    @Override
    public boolean supports(DocumentationType documentationType) {
        return DocumentationType.OAS_30.equals(documentationType);
    }
}
