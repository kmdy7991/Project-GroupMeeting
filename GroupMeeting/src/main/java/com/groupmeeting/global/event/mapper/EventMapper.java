package com.groupmeeting.global.event.mapper;

import com.groupmeeting.global.event.dto.ErrorAlertEvent;
import com.groupmeeting.global.event.dto.ErrorAlertMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EventMapper {
    public static ErrorAlertEvent supplyErrorAlertEventBy(
            final Exception exception,
            final HttpServletRequest request
    ) {
        return new ErrorAlertEvent(exception, request);
    }

    public static ErrorAlertMessage supplyErrorAlertEventBy(final ErrorAlertEvent event) {
        final String trace = Arrays.toString(event.exception().getStackTrace());
        final HttpServletRequest request = event.request();

        return new ErrorAlertMessage(
                trace,
                request.getContextPath(),
                request.getRequestURL().toString(),
                request.getMethod(),
                request.getParameterMap(),
                request.getRemoteAddr(),
                request.getHeader("User-Agent")
        );
    }
}
