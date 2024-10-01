package com.groupmeeting.auth.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupmeeting.core.exception.custom.JwtException;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

import static com.groupmeeting.global.enums.ExceptionReturnCode.*;


public interface OidcProvider {
    String getProviderId(String idToken);

    // readValue() Method로 생성되는 맵의 Type이 Map<String, String>인지 컴파일러는 알 수 없음, 해당 어노테이션으로 Warning 제거
    @SuppressWarnings("unchecked")
    default Map<String, String> parseHeaders(String token) {
        String header = token.split("\\.")[0];

        try {
            return new ObjectMapper().readValue(Base64.getUrlDecoder().decode(header), Map.class);
        } catch (IOException e) {
            throw new JwtException(INTERNAL_SERVER_ERROR);
        }
    }
}
