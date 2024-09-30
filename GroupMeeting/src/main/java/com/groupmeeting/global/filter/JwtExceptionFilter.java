package com.groupmeeting.global.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.groupmeeting.global.enums.ExceptionReturnCode;
import com.groupmeeting.global.exception.custom.JwtException;
import com.groupmeeting.global.exception.response.ExceptionResponse;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jetbrains.annotations.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            FilterChain filterChain
    ) throws
            ServletException,
            IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (JwtException exception) {
            setErrorResponse(exception.getExceptionReturnCode(), response);
        }
    }

    private void setErrorResponse(ExceptionReturnCode returnCode, HttpServletResponse response) {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json; charset=UTF-8");

        try {
            response.getWriter().write(toJson(
                    new ExceptionResponse<>(
                            returnCode.getCode(),
                            returnCode.getMessage(),
                            null
            )));
        } catch (IOException ignored) {
        }
    }

    private String toJson(Object data) throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(data);
    }
}