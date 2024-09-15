package com.groupmeeting.global.exception.custom;

public class UnAuthorizedException extends Exception {
    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException() {
        super();
    }
}
