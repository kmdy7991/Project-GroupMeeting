package com.groupmeeting.global.exception;

import com.groupmeeting.global.exception.custom.*;
import com.groupmeeting.global.utils.CustomResponse;
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
                                HttpStatus.NOT_FOUND.toString(),
                                e.getMessage() != null ? e.getMessage() : "",
                                null
                        )
                );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<CustomResponse<Object>> handleValidationException(HandlerMethodValidationException e) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new CustomResponse<>(
                                HttpStatus.UNPROCESSABLE_ENTITY.toString(),
                                e.getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<CustomResponse<Object>> handleJwtExceptionException(JwtException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse<>(
                                e.getExceptionReturnCode().getCode(),
                                e.getExceptionReturnCode().getMessage(),
                                null
                        )
                );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<CustomResponse<Object>> handleBadRequestException(BadRequestException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse<>(
                                HttpStatus.BAD_REQUEST.toString(),
                                e.getMessage() != null ? e.getMessage() : "",
                                null
                        )
                );
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<CustomResponse<Object>> handleUnauthorizedException(UnAuthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new CustomResponse<>(
                        HttpStatus.UNAUTHORIZED.toString(),
                        e.getMessage() != null ? e.getMessage() : "",
                        null)
                );
    }

    @ExceptionHandler(NotImageRequestException.class)
    public ResponseEntity<CustomResponse<Object>> handleNotImageException(UnAuthorizedException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new CustomResponse<>(
                                HttpStatus.UNAUTHORIZED.toString(),
                                e.getMessage() != null ? e.getMessage() : "",
                                null
                        )
                );
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomResponse<Object>> handleOtherException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CustomResponse<>(
                                HttpStatus.INTERNAL_SERVER_ERROR.toString(),
                                String.format("%s with stack trace: %s", e.getMessage(), getStackTraceConvertString(e)),
                                null
                        )
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
