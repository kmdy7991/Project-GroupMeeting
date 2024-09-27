package com.groupmeeting.auth.provider;

import com.groupmeeting.auth.key.OidcPublicKeyList;
import com.groupmeeting.global.client.AppleAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.PublicKey;

@Component
@RequiredArgsConstructor
public class AppleAuthProvider implements OidcProvider {
    private final AppleAuthClient appleClient;
    private final JwtProvider jwtProvider;
    private final PublicKeyProvider publicKeyProvider;

    @Override
    public String getProviderId(final String idToken) {
        final OidcPublicKeyList oidcPublicKeyList = appleClient.getPublicKeys();
        final PublicKey publicKey = publicKeyProvider.generatePublicKey(parseHeaders(idToken), oidcPublicKeyList);

        return jwtProvider.parseClaims(idToken, publicKey).getSubject();
    }
}
