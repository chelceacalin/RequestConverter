package com.requestConverter.RequestConverter.filters;

import com.requestConverter.RequestConverter.exception.InvalidUsernameException;
import com.requestConverter.RequestConverter.util.AppContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Component
@Order(2)
public class AuthFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String username = request.getHeader("username");
        if (username == null || username.isEmpty()) {
            if (request.getHeader("host") != null && request.getHeader("host").equals("localhost:8080")) {
                username = "browser";
                log.info("Will let request pass since it comes from browser");
            } else {
                throw new InvalidUsernameException("Username is empty");
            }
        }

        AppContext context = AppContext.getAppContext();
        context.setUsername(username);
        context.setLocalDateTime(LocalDateTime.now());
        AppContext.setAppContext(context);

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        AppContext.clear();
    }
}
