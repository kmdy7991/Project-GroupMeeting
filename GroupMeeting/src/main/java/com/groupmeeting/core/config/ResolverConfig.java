package com.groupmeeting.core.config;

import com.groupmeeting.core.resolver.AccessTokenResolver;
import com.groupmeeting.core.resolver.AuthorizedUserResolver;
import com.groupmeeting.core.resolver.RefreshTokenResolver;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class ResolverConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(new AuthorizedUserResolver());
        resolvers.add(new AccessTokenResolver());
        resolvers.add(new RefreshTokenResolver());
    }
}
