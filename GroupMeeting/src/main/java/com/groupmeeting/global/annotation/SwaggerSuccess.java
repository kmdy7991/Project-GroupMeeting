package com.groupmeeting.global.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Operation
@ApiResponses(value = {
        @ApiResponse(responseCode = "200")
})
public @interface SwaggerSuccess {
}
