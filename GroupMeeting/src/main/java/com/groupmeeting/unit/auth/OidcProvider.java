package com.groupmeeting.unit.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupmeeting.global.exception.custom.JwtException;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import static com.groupmeeting.global.enums.ExceptionReturnCode.*;


public interface OidcProvider {
    String getProviderId(String idToken);

    default Map<String, String> parseHeaders(String token) {
        String header = token.split("\\.")[0];

        try {
            return new ObjectMapper().readValue(Base64.getUrlDecoder().decode(header), Map.class);
        } catch (IOException e) {
            throw new JwtException(INTERNAL_SERVER_ERROR);
        }
    }
}
