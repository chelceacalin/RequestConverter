package com.requestConverter.RequestConverter.filters;

import com.requestConverter.RequestConverter.exception.InvalidUsernameException;
import com.requestConverter.RequestConverter.util.AppContext;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.mockito.Mockito.*;

@SpringBootTest
@SuppressWarnings("DSL_SCOPE_VIOLATION")
class AuthFilterTest {
    private AuthFilter authFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;

    @BeforeEach
    void setUp() {
        authFilter = new AuthFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        filterChain = mock(FilterChain.class);
    }

    @Test
    void doFilter_validUsername_shouldPass() throws ServletException, IOException {
        when(request.getHeader("username")).thenReturn("testUser");
        authFilter.doFilter(request, response, filterChain);

        verify(filterChain, times(1)).doFilter(request, response);
    }

    @Test
    void doFilter_noUsername_shouldThrowException() {
        when(request.getHeader("username")).thenReturn(null);
        Assertions.assertThrows(InvalidUsernameException.class, () -> authFilter.doFilter(request, response, filterChain));
    }

    @Test
    void letBrowserRequestsPass() throws ServletException, IOException {
        when(request.getHeader("username")).thenReturn(null);
        when(request.getHeader("host")).thenReturn("localhost:8080");

        authFilter.doFilter(request, response, filterChain);
        Assertions.assertEquals(AppContext.getAppContext().getUsername(),"browser");
    }
}