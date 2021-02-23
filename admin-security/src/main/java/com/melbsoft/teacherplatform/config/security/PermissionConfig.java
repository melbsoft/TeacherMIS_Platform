package com.melbsoft.teacherplatform.config.security;

import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

import javax.annotation.Resource;


@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class PermissionConfig extends GlobalMethodSecurityConfiguration {

    @Resource
    RBACPermissionEvaluator rbacPermissionEvaluator;


    @Override
    protected MethodSecurityExpressionHandler createExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        expressionHandler.setPermissionEvaluator(rbacPermissionEvaluator);
        return expressionHandler;
    }


}
