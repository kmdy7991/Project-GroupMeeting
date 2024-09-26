package com.groupmeeting.global.resolver;

import com.groupmeeting.global.annotation.auth.RefreshToken;
import com.groupmeeting.global.enums.ExceptionReturnCode;
import com.groupmeeting.global.exception.custom.JwtException;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static com.groupmeeting.global.enums.ExceptionReturnCode.*;
import static org.springframework.util.StringUtils.hasLength;

public class RefreshTokenResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RefreshToken.class)
                && String.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public String resolveArgument(
            MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) throws Exception {
        final String refreshToken = webRequest.getParameter("Refresh");

        if(!hasLength(refreshToken)){
            throw new JwtException(EMPTY_REFRESH);
        }

        return refreshToken;
    }
}
