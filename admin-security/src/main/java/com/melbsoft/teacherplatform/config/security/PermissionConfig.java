package com.melbsoft.teacherplatform.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;


@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class PermissionConfig extends GlobalMethodSecurityConfiguration {

    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(rbacPermissionEvaluator());
        return expressionHandler;
    }

    @Bean
    public PermissionEvaluator rbacPermissionEvaluator() {
        return new RBACPermissionEvaluator();
    }


}
