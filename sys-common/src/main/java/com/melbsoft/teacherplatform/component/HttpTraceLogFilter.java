package com.melbsoft.teacherplatform.component;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.MarkerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;
import org.springframework.web.util.WebUtils;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Objects;

@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@WebFilter(urlPatterns = "/*")
@Component
@ConditionalOnProperty("log.access.trace")
public class HttpTraceLogFilter extends OncePerRequestFilter {

    String[] contents = new String[]{"application/json", "application/x-www-form-urlencoded"};
    @Resource
    ObjectMapper jsonMapper;

    @Override
    protected void initFilterBean() throws ServletException {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (invalid(request)) {
            filterChain.doFilter(request, response);
            return;
        }
        if (!(request instanceof ContentCachingRequestWrapper)) {
            request = new ContentCachingRequestWrapper(request);
        }
        if (!(response instanceof ContentCachingResponseWrapper)) {
            response = new ContentCachingResponseWrapper(response);
        }
        int status = HttpStatus.INTERNAL_SERVER_ERROR.value();
        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
            status = response.getStatus();
        } finally {
            long cost = System.currentTimeMillis() - startTime;
            HttpTraceLog traceLog = HttpTraceLog
                    .builder()
                    .path(request.getRequestURI())
                    .method(request.getMethod())
                    .status(status)
                    .cost(cost)
                    .build();
            if (Strings.isNotBlank(request.getQueryString())) {
                traceLog.setReqQuery(request.getQueryString());
            }
            traceLog.setReqBody(getRequestBody(request));
            traceLog.setRespBody(getResponseBody(response));
            log.info(MarkerFactory.getMarker("TRACE"), "{}", jsonMapper.writeValueAsString(traceLog));

            updateResponse(response);
        }
    }

    private boolean invalid(HttpServletRequest request) {
        String requestType = request.getContentType();
        if (Strings.isBlank(requestType)) {
            return false;
        }
        for (String c : contents) {
            if (requestType.contains(c)) {
                return false;
            }
        }
        return true;
    }

    private String getRequestBody(HttpServletRequest request) {
        String requestBody = null;
        ContentCachingRequestWrapper wrapper = WebUtils.getNativeRequest(request, ContentCachingRequestWrapper.class);
        if (wrapper != null) {
            try {
                requestBody = new String(wrapper.getContentAsByteArray(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("trace requestBody fail！ {}", e);
            }
            // NOOP
        }
        return Strings.isBlank(requestBody) ? null : requestBody;
    }

    private String getResponseBody(HttpServletResponse response) {
        String responseBody = null;
        ContentCachingResponseWrapper wrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        if (wrapper != null) {
            try {
                responseBody = new String(wrapper.getContentAsByteArray(), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                log.error("trace responseBody fail！ {}", e);
            }
        }
        return Strings.isBlank(responseBody) ? null : responseBody;
    }

    private void updateResponse(HttpServletResponse response) throws IOException {
        ContentCachingResponseWrapper responseWrapper = WebUtils.getNativeResponse(response, ContentCachingResponseWrapper.class);
        Objects.requireNonNull(responseWrapper).copyBodyToResponse();
    }


    @Data
    @Builder
    private static class HttpTraceLog {
        private String path;
        private String method;
        private Integer status;
        private String reqQuery;
        @JsonRawValue
        private String reqBody;
        @JsonRawValue
        private String respBody;
        private Long cost;
    }


}
