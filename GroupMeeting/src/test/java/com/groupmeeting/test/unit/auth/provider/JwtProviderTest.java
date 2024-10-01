package com.groupmeeting.test.unit.auth.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groupmeeting.auth.provider.impl.JwtProvider;
import com.groupmeeting.auth.token.JsonWebToken;
import com.groupmeeting.entity.user.User;
import com.groupmeeting.global.enums.ExceptionReturnCode;
import com.groupmeeting.global.enums.Role;
import com.groupmeeting.core.exception.custom.JwtException;
import com.groupmeeting.test.base.object.MockitoTest;
import com.groupmeeting.core.config.JwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.Authentication;
import org.springframework.test.util.ReflectionTestUtils;

import javax.crypto.SecretKey;

import static com.groupmeeting.test.base.util.TestUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@Import(JwtConfig.class)
public class JwtProviderTest extends MockitoTest {
    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private SecretKey secretKey;

    @InjectMocks
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(jwtProvider, "accessTokenExpired", ACCESS_TOKEN_EXPIRED_SECOND);
        ReflectionTestUtils.setField(jwtProvider, "refreshTokenExpired", REFRESH_TOKEN_EXPIRED_SECOND);
        ReflectionTestUtils.setField(jwtProvider, "grantType", GRANT_TYPE);
        ReflectionTestUtils.setField(jwtProvider, "userKey", USER_KEY);
        ReflectionTestUtils.setField(jwtProvider, "secretKey", Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY)));
    }

    @Test
    @DisplayName("토큰 생성에 성공")
    void generateJwt() {
        User user = getConstructorMonkey().giveMeOne(User.class);

        JsonWebToken jwt = jwtProvider.generateToken(user);

        assertNotNull(jwt.accessToken());
        assertNotNull(jwt.refreshToken());
        assertNotNull(jwt.grantType());
    }

    @Test
    @DisplayName("유저가 null이면 토큰 생성 실패")
    void failGenerateJwt() {
        User user = null;
        assertThrows(
                JwtException.class,
                () -> jwtProvider.generateToken(user),
                ExceptionReturnCode.EMPTY_USER.getMessage()
        );
    }

    @Test
    @DisplayName("토큰으로부터 인증 정보 확인")
    void getAuthentication() {
        User user = getConstructorMonkey().giveMeOne(User.class);

        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "role", Role.USER);

        String accessToken = jwtProvider.generateToken(user).accessToken();

        Authentication authentication = jwtProvider.getAuthentication(accessToken);

        assertEquals(authentication.getAuthorities().toString(), "[%s]".formatted(Role.USER.securityRole()));
    }

    @Test
    @DisplayName("토큰으로 부터 Claim 정보 확인")
    void parseClaims() {
        User user = getConstructorMonkey().giveMeOne(User.class);

        ReflectionTestUtils.setField(user, "id", 1L);
        ReflectionTestUtils.setField(user, "role", Role.USER);

        String accessToken = jwtProvider.generateToken(user).accessToken();

        Claims claims = jwtProvider.parseClaims(accessToken);

        assertNotNull(claims);
    }
}
