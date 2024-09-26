package com.groupmeeting.auth;

import com.groupmeeting.global.client.KakaoAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.AuthProvider;
import java.security.PublicKey;

@Component
@RequiredArgsConstructor
public class KakaoAuthProvider implements OidcProvider {
    private final KakaoAuthClient kakaoClient;
    private final JwtProvider jwtProvider;
    private final PublicKeyProvider publicKeyProvider;

    @Override
    public String getProviderId(final String idToken) {
        final OidcPublicKeyList oidcPublicKeyList = kakaoClient.getPublicKeys();
        final PublicKey publicKey = publicKeyProvider.generatePublicKey(parseHeaders(idToken), oidcPublicKeyList);

        return jwtProvider.parseClaims(idToken, publicKey).getSubject();
    }
}
