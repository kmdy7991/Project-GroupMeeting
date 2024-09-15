package com.groupmeeting.global.exception;

import com.groupmeeting.global.exception.custom.BadRequestException;
import com.groupmeeting.global.exception.custom.ResourceNotFoundException;
import com.groupmeeting.global.exception.custom.UnAuthorizedException;
import com.groupmeeting.global.util.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<CustomResponse<Object>> handleEntityNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new CustomResponse<>(
                        HttpStatus.NOT_FOUND.value(),
                        null,
                        e.getMessage() != null ? e.getMessage() : "")
                );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CustomResponse<Object>> handleValidationException(HandlerMethodValidationException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new CustomResponse<>(
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        null,
                        e.getMessage())
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomResponse<Object>> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse<>(
                        HttpStatus.BAD_REQUEST.value(),
                        null,
                        e.getMessage() != null ? e.getMessage() : "")
                );
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<CustomResponse<Object>> handleUnauthorizedException(UnAuthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CustomResponse<>(
                        HttpStatus.UNAUTHORIZED.value(),
                        null,
                        e.getMessage() != null ? e.getMessage() : "")
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Object>> handleOtherException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomResponse<>(
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        null,
                        String.format("%s with stack trace: %s", e.getMessage(), getStackTraceConvertString(e)))
                );
    }

    private String getStackTraceConvertString(Exception e) {
        StringBuilder sb = new StringBuilder();

        for (StackTraceElement ste : e.getStackTrace()) {
            sb.append(ste.toString()).append("\n");
        }

        return sb.toString();
    }
}
