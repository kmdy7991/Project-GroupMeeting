package com.groupmeeting.global.event.dto;

import jakarta.servlet.http.HttpServletRequest;

public record ErrorAlertEvent(
        Exception exception,
        HttpServletRequest request
) {
}