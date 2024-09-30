package com.groupmeeting.auth.provider;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.groupmeeting.auth.token.JsonWebToken;
import com.groupmeeting.auth.oauth.OAuthUserDetails;
import com.groupmeeting.entity.user.User;
import com.groupmeeting.global.exception.custom.JwtException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.InvalidKeyException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.security.PublicKey;

import java.util.Date;

import static com.groupmeeting.global.enums.ExceptionReturnCode.*;
import static java.util.Objects.isNull;

@Component
public class JwtProvider {
    private final SecretKey secretKey;
    private final Long accessTokenExpired;
    private final Long refreshTokenExpired;
    private final String grantType;
    private final String userKey;

    public JwtProvider(
            SecretKey secretKey,
            @Value("${jwt.expire.access}") Long accessTokenExpired,
            @Value("${jwt.expire.refresh}") Long refreshTokenExpired,
            @Value("${jwt.grant-type}") String grantType,
            @Value("${jwt.user-key}") String userKey
    ) {
        this.secretKey = secretKey;
        this.accessTokenExpired = accessTokenExpired;
        this.refreshTokenExpired = refreshTokenExpired;
        this.grantType = grantType;
        this.userKey = userKey;
    }

    public JsonWebToken generateToken(final User user) {
        if (isNull(user)) {
            throw new JwtException(EMPTY_USER);
        }

        try {
            final String accessToken =
                    Jwts.builder()
                            .claim(userKey, user)
                            .expiration(new Date(System.currentTimeMillis() + accessTokenExpired))
                            .signWith(secretKey, Jwts.SIG.HS512)
                            .compact();

            final String refreshToken =
                    Jwts.builder()
                            .claim(userKey, user)
                            .expiration(new Date(System.currentTimeMillis() + refreshTokenExpired))
                            .signWith(secretKey, Jwts.SIG.HS512)
                            .compact();

            return JsonWebToken.builder()
                    .grantType(grantType)
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

        } catch (InvalidKeyException e) {
            throw new JwtException(INVALID_KEY);
        }
    }

    public Authentication getAuthentication(String token) {
        final User user = getUser(token);
        final OAuthUserDetails userDetails = new OAuthUserDetails(user);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public User getUser(String token) {
        final Claims claims = parseClaims(token);

        if (isNull(claims.get(userKey))) {
            throw new JwtException(EMPTY_AUTH_JWT);
        }

        return new ObjectMapper().convertValue(claims.get(userKey), User.class);
    }

    public long getTokenExpiredSecond(final String token) {
        final Claims claims = parseClaims(token);
        return claims.getExpiration().getTime();
    }

    public long getAccessTokenExpiredSecond() {
        return this.accessTokenExpired;
    }

    public long getRefreshTokenExpiredSecond() {
        return this.refreshTokenExpired;
    }

    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            throw new JwtException(EXPIRED_JWT_TOKEN);
        } catch (RuntimeException e) {
            throw new JwtException(WRONG_JWT_TOKEN);
        }
    }

    public Claims parseClaims(String token, PublicKey publicKey) {
        try {
            return Jwts.parser()
                    .verifyWith(publicKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();

        } catch (ExpiredJwtException e) {
            throw new JwtException(EXPIRED_JWT_TOKEN);
        } catch (RuntimeException e) {
            throw new JwtException(WRONG_JWT_TOKEN);
        }
    }
}