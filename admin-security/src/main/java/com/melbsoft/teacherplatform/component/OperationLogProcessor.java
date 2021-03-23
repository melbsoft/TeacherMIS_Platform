//package com.melbsoft.teacherplatform.component;
//
//import com.google.common.collect.Maps;
//import com.google.common.collect.Sets;
//import com.googlecode.aviator.AviatorEvaluator;
//import com.googlecode.aviator.Expression;
//import com.melbsoft.teacherplatform.dao.admin.OperationLogMapper;
//import com.melbsoft.teacherplatform.model.admin.OperationLog;
//import com.melbsoft.teacherplatform.tools.OpLogHelper;
//import com.melbsoft.teacherplatform.tools.SecurityHelper;
//import com.melbsoft.teacherplatform.web.basic.ResultMessage;
//import com.melbsoft.teacherplatform.web.exception.ForbiddenException;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.logging.log4j.util.Strings;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.Resource;
//import java.time.LocalDateTime;
//import java.util.HashSet;
//import java.util.Map;
//
//
//@Slf4j
//@Aspect
//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class OperationLogProcessor {
//    @Resource
//    OperationLogMapper operationLogMapper;
//
//    @Around("@annotation(com.melbsoft.teacherplatform.component.OpLog)")
//    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
//        try {
//            Object[] params = joinPoint.getArgs();
//            MethodSignature sign = (MethodSignature) joinPoint.getSignature();
//            OpLog optLog = sign.getMethod().getAnnotation(OpLog.class);
//            String operationName = optLog.value();
//            String target = optLog.target();
//            String operator = SecurityHelper.getUserDetails().getUsername();
//            if (Strings.isBlank(target) || Strings.isBlank(operationName)) {
//                throw new ForbiddenException("empty OpLog!");
//            }
//            Object resultObject = joinPoint.proceed();
//            if (resultObject != null) {
//                ResultMessage m = (ResultMessage) resultObject;
//                if (m.getCode().equals(ResultMessage.SUCCESS.getCode())) {
//                    OperationLog.OperationLogBuilder logBuilder = OperationLog.builder();
//                    logBuilder.operationTime(LocalDateTime.now());
//                    logBuilder.operationName(operationName);
//                    logBuilder.operator(operator);
//                    logBuilder.target(parseExpress(target, sign, params));
//                    logBuilder.result(m.getMessage());
//                    save(logBuilder.build());
//                }
//            }
//            return resultObject;
//        } catch (Throwable e) {
//            throw e;
//        }
//    }
//
//    private String parseExpress(String target, MethodSignature sign, Object[] params) {
//        Expression expression = AviatorEvaluator.compile(target);
//        HashSet<String> vars = Sets.newHashSet(expression.getVariableNames());
//        Map<String, Object> content = Maps.newHashMap();
//        if (vars.contains("method")) {
//            content.put("method", sign.getName());
//        }
//        if (vars.contains("operator")) {
//            content.put("operator", SecurityHelper.getUserDetails().getUsername());
//        }
//        String[] paramNames = sign.getParameterNames();
//        for (int i = 0; i < paramNames.length; i++) {
//            if (vars.contains(paramNames[i])) {
//                content.put(paramNames[i], params[i]);
//            }
//        }
//        content.putAll(OpLogHelper.pop());
//        return (String) expression.execute(content);
//    }
//
//    private void save(OperationLog log) {
//        operationLogMapper.save(log);
//    }
//
//
//}
