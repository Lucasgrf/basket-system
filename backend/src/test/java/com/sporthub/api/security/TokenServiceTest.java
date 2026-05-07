package com.sporthub.api.security;

import com.sporthub.api.model.User;
import com.sporthub.api.model.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("TokenService Unit Tests")
class TokenServiceTest {

    private TokenService tokenService;

    @BeforeEach
    void setUp() {
        tokenService = new TokenService();
        ReflectionTestUtils.setField(tokenService, "secret", "test-secret-key-for-unit-tests-only");
        ReflectionTestUtils.setField(tokenService, "expirationHours", 2);
    }

    private User buildUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john.doe");
        user.setRole(Role.ADMIN);
        return user;
    }

    @Test
    @DisplayName("generateToken - should produce a non-blank token")
    void generateToken_shouldReturnNonBlankToken() {
        String token = tokenService.generateToken(buildUser());
        assertThat(token).isNotBlank();
    }

    @Test
    @DisplayName("validateToken - should return subject for a valid token")
    void validateToken_shouldReturnSubject_whenTokenValid() {
        User user = buildUser();
        String token = tokenService.generateToken(user);

        String subject = tokenService.validateToken(token);

        assertThat(subject).isEqualTo("john.doe");
    }

    @Test
    @DisplayName("validateToken - should return null for a tampered token")
    void validateToken_shouldReturnNull_whenTokenTampered() {
        String result = tokenService.validateToken("this.is.not.valid");
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("validateToken - should return null for an empty string")
    void validateToken_shouldReturnNull_whenTokenBlank() {
        assertThat(tokenService.validateToken("")).isNull();
    }

    @Test
    @DisplayName("generateToken - different users should produce different tokens")
    void generateToken_differentUsers_shouldProduceDifferentTokens() {
        User user1 = buildUser();

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("jane.smith");
        user2.setRole(Role.TECHNICIAN);

        String token1 = tokenService.generateToken(user1);
        String token2 = tokenService.generateToken(user2);

        assertThat(token1).isNotEqualTo(token2);
    }

    @Test
    @DisplayName("decodeToken - should contain userId and role claims")
    void decodeToken_shouldContainExpectedClaims() {
        User user = buildUser();
        String token = tokenService.generateToken(user);
        var decoded = tokenService.decodeToken(token);

        assertThat(decoded.getClaim("userId").asLong()).isEqualTo(1L);
        assertThat(decoded.getClaim("role").asString()).isEqualTo("ADMIN");
        assertThat(decoded.getSubject()).isEqualTo("john.doe");
    }
}
