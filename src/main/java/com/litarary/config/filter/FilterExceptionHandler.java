package com.litarary.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.litarary.common.ErrorCode;
import com.litarary.common.exception.filter.JwtErrorException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class FilterExceptionHandler extends OncePerRequestFilter {

    private final ObjectMapper objectMapper;
    private static final String CHARSET = "UTF-8";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtErrorException ex) {
            setJwtErrorMessage(response, ex);
        }
    }

    private void setJwtErrorMessage(HttpServletResponse response, JwtErrorException ex) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(CHARSET);

        objectMapper.writeValue(response.getWriter(), new JwtFilterError(ex.getErrorCode(), ex.getMessage()));
    }

    @RequiredArgsConstructor
    @Getter
    public class JwtFilterError {
        private final ErrorCode errorCode;
        private final String errorMessage;
    }
}
