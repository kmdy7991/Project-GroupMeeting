package com.groupmeeting.core.exception.custom;

public class UnAuthorizedException extends Exception {
    public UnAuthorizedException(String message) {
        super(message);
    }

    public UnAuthorizedException() {
        super();
    }
}
