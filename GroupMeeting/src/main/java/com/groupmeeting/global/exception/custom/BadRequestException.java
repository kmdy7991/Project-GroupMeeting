package com.groupmeeting.global.exception.custom;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super();
    }
}
