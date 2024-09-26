package com.groupmeeting.global.exception.custom;

import com.groupmeeting.global.enums.ExceptionReturnCode;
import lombok.Getter;

@Getter
public class JwtException extends RuntimeException {
    private final ExceptionReturnCode exceptionReturnCode;

    public JwtException(ExceptionReturnCode exceptionReturnCode) {
        super();
        this.exceptionReturnCode = exceptionReturnCode;
    }
}
