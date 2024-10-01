package com.groupmeeting.auth.token;

import lombok.Builder;

@Builder
public record JsonWebToken(
        String accessToken,
        String refreshToken,
        String grantType
) {
}
