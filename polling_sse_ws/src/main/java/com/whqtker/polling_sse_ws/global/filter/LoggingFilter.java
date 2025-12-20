package com.whqtker.polling_sse_ws.global.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

@Component
public class LoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // SSE 연결은 스트리밍 방식이므로 응답을 캐싱하면 안 됨 (연결이 바로 끊기거나 버퍼링됨)
        if (request.getRequestURI().startsWith("/sse")) {
            filterChain.doFilter(request, response);
            return;
        }

        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(wrappedRequest, wrappedResponse);
        } finally {
            logRequest(wrappedRequest);
            logResponse(wrappedResponse);
            wrappedResponse.copyBodyToResponse();
        }
    }

    private void logRequest(ContentCachingRequestWrapper request) {
        StringBuilder headers = new StringBuilder();
        Collections.list(request.getHeaderNames()).forEach(name ->
                headers.append("\n    ").append(name).append(": ").append(request.getHeader(name)));

        String body = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);

        log.info("""
                
                ==================== REQUEST ====================
                {} {}
                Headers:{}
                Body:
                {}
                ==================================================
                """,
                request.getMethod(),
                request.getRequestURI(),
                headers.isEmpty() ? " (none)" : headers,
                body.isEmpty() ? "(empty)" : body);
    }

    private void logResponse(ContentCachingResponseWrapper response) {
        StringBuilder headers = new StringBuilder();
        response.getHeaderNames().forEach(name ->
                headers.append("\n    ").append(name).append(": ").append(response.getHeader(name)));

        String body = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

        log.info("""
                
                ==================== RESPONSE ====================
                Status: {}
                Headers:{}
                Body:
                {}
                ===================================================
                """,
                response.getStatus(),
                headers.isEmpty() ? " (none)" : headers,
                body.isEmpty() ? "(empty)" : body);
    }
}
