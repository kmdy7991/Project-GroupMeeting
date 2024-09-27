package com.groupmeeting.unit.auth;

public record OidcPublicKey(
        String kid,
        String kty,
        String alg,
        String use,
        String n,
        String e
) {
}
