//package com.melbsoft.teacherplatform.component;
//
//import org.springframework.stereotype.Component;
//
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Component
//public class CORSFilter implements Filter {
//
//    @Override
//    public void destroy() { }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
//        String origin = httpServletRequest.getHeader("Origin");
//        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Authorization,X-CustomHeader,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type");
//        httpServletResponse.setHeader("Access-Control-Allow-Methods", "OPTIONS,GET,POST,DELETE,PUT");
//        if(httpServletRequest.getMethod().equals("OPTIONS")) {
//            httpServletResponse.setStatus(200);
//        }
//        chain.doFilter(httpServletRequest, httpServletResponse);
//    }
//
//    @Override
//    public void init(FilterConfig arg0) throws ServletException { }
//}
