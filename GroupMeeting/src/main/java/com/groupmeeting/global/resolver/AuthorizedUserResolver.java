package com.groupmeeting.global.resolver;

import com.groupmeeting.auth.oauth.OAuthUserDetails;

import com.groupmeeting.entity.user.User;

import com.groupmeeting.global.annotation.auth.SignUser;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;

import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import static java.util.Objects.isNull;

public class AuthorizedUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(SignUser.class)
                && User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public User resolveArgument(
            @NotNull MethodParameter parameter,
            ModelAndViewContainer mavContainer,
            @NotNull NativeWebRequest webRequest,
            WebDataBinderFactory binderFactory
    ) {
        final OAuthUserDetails userDetails = (OAuthUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return isNull(userDetails) ? null : userDetails.getUser();
    }
}
