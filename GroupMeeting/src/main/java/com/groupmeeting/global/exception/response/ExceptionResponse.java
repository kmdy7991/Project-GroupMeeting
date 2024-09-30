package com.groupmeeting.global.exception.response;

public record ExceptionResponse<T>(String code, String message, T data) {
}
