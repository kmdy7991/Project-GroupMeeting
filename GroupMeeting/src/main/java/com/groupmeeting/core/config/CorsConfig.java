package com.groupmeeting.core.config;

import jakarta.annotation.Nullable;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Getter
@Configuration
@ConfigurationProperties("cors")
public class CorsConfig {
    private List<String> allowedOrigins;
    @Nullable private List<String> allowedMethods;
    @Nullable private List<String> allowedHeaders;
}
