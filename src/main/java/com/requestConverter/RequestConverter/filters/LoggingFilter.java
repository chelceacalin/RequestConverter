package com.requestConverter.RequestConverter.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
@Order(1)
@Slf4j
public class LoggingFilter implements Filter {

    @Value("${custom.headersToHide}")
    List<String> headersToHide;
    @Value("${custom.hideDefaultHeaders}")
    boolean hideDefaultHeaders;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (!(request instanceof HttpServletRequest httpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        ContentCachingRequestWrapper wrappedRequest = new ContentCachingRequestWrapper(httpServletRequest);
        chain.doFilter(wrappedRequest, response);
        logRequest(wrappedRequest);
    }

    public void logRequest(ContentCachingRequestWrapper wrappedRequest) {
        String method = wrappedRequest.getMethod();
        StringBuilder curlRequest = new StringBuilder("curl");
        ServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(wrappedRequest);
        curlRequest.append(" -X ")
                .append(method)
                .append(" ")
                .append(wrappedRequest.getRequestURL())
                .append(" ");

        HttpHeaders httpHeaders = getFilteredHeaders(serverHttpRequest.getHeaders());

        httpHeaders.forEach((k, v) -> {
            curlRequest.append("-H ")
                    .append("'")
                    .append(k)
                    .append(": ")
                    .append(v.getFirst())
                    .append("' ");
        });

        String body = getCachedBody(wrappedRequest);
        if (!body.isEmpty()) {
            curlRequest.append("--data '").append(body).append("'");
        }


        log.info("Received call: {}", curlRequest);
    }


    private String getCachedBody(ContentCachingRequestWrapper request) {
        byte[] body = request.getContentAsByteArray();
        return (body.length > 0) ? new String(body, StandardCharsets.UTF_8) : "";
    }


    private HttpHeaders getFilteredHeaders(HttpHeaders requestHeaders) {
        if (!hideDefaultHeaders) {
            return requestHeaders;
        }
        HttpHeaders filteredHeaders = new HttpHeaders();
        requestHeaders.forEach((key, value) -> {
            if (!headersToHide.contains(key)) {
                filteredHeaders.put(key, List.copyOf(value));
            }
        });
        return filteredHeaders;
    }
}
