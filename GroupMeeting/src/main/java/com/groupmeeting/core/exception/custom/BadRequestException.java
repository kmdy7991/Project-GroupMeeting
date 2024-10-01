package com.groupmeeting.core.exception.custom;

public class BadRequestException extends Exception {
    public BadRequestException(String message) {
        super(message);
    }

    public BadRequestException() {
        super();
    }
}
