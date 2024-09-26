package com.groupmeeting.auth;

import lombok.Builder;

@Builder
public record JsonWebToken(
        String accessToken,
        String refreshToken,
        String grantType) {
}
