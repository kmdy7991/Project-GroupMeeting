package com.groupmeeting.core.exception.custom;

public class AuthException extends RuntimeException {
    public AuthException(String message) {
        super(message);
    }

    public AuthException() {
        super();
    }
}
