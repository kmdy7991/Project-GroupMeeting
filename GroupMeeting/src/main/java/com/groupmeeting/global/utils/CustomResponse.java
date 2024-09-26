package com.groupmeeting.global.utils;

public record CustomResponse<T>(String code, String message, T data) {
}
