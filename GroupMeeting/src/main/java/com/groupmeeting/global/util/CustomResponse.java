package com.groupmeeting.global.util;

public record CustomResponse<T>(int code, T data, String message) {
}
