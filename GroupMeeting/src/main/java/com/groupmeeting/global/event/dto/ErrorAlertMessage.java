package com.groupmeeting.global.event.dto;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class ErrorAlertMessage implements Define {
    String trace;
    @Nullable
    String contextPath;
    @Nullable
    String requestUrl;
    @Nullable
    String method;
    @Nullable
    Map<String, String[]> parameterMap;
    @Nullable
    String remoteAddress;
    @Nullable
    String header;
}
