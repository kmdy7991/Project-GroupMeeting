package com.groupmeeting.core.filter;

import com.groupmeeting.auth.provider.impl.JwtProvider;

import com.groupmeeting.global.enums.ExceptionReturnCode;
import com.groupmeeting.core.exception.custom.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtProvider jwtProvider;
    private final String accessHeader;
    private final String grantType;

    public JwtAuthenticationFilter(
            JwtProvider jwtProvider,
            @Value("${jwt.access-header}") String accessHeader,
            @Value("${jwt.grant-type}") String grantType
    ) {
        this.jwtProvider = jwtProvider;
        this.accessHeader = accessHeader;
        this.grantType = grantType;
    }

    @Override
    protected void doFilterInternal(
            @NotNull HttpServletRequest request,
            @NotNull HttpServletResponse response,
            FilterChain filterChain
    ) throws
            ServletException,
            IOException
    {
        Optional<String> token = getTokensFromHeader(request, accessHeader);

        token.ifPresent(it -> {
            String accessToken = replaceBearerToBlank(it);

            Authentication authentication = jwtProvider.getAuthentication(accessToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        });

        filterChain.doFilter(request, response);
    }

    private Optional<String> getTokensFromHeader(
            HttpServletRequest request,
            String header)
    {
        return Optional.ofNullable(request.getHeader(header));
    }

    private String replaceBearerToBlank(String token) {
        String suffix = grantType + " ";

        if (!token.startsWith(suffix)) {
            throw new JwtException(ExceptionReturnCode.NOT_EXIST_BEARER_SUFFIX);
        }

        return token.replace(suffix, "");
    }
}
