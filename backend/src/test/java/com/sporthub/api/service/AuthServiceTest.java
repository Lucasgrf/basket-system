package com.sporthub.api.service;

import com.sporthub.api.dto.request.LoginRequest;
import com.sporthub.api.dto.request.RegisterRequest;
import com.sporthub.api.dto.response.AuthResponse;
import com.sporthub.api.exception.BusinessRuleException;
import com.sporthub.api.exception.ResourceAlreadyExistsException;
import com.sporthub.api.model.User;
import com.sporthub.api.model.enums.Role;
import com.sporthub.api.repository.UserRepository;
import com.sporthub.api.security.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("AuthService Unit Tests")
class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthService authService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("john.doe");
        user.setEmail("john@example.com");
        user.setPassword("hashed-password");
        user.setRole(Role.ATHLETE);
    }

    // --- Register ---

    @Test
    @DisplayName("register - should create user and return JWT")
    void register_shouldCreateUserAndReturnToken() {
        var request = new RegisterRequest("john.doe", "password123", "john@example.com", null);

        given(userRepository.findByUsername("john.doe")).willReturn(Optional.empty());
        given(userRepository.findByEmail("john@example.com")).willReturn(Optional.empty());
        given(passwordEncoder.encode("password123")).willReturn("hashed-password");
        given(userRepository.save(any(User.class))).willReturn(user);
        given(tokenService.generateToken(user)).willReturn("jwt-token");

        AuthResponse result = authService.register(request);

        assertThat(result.token()).isEqualTo("jwt-token");
        assertThat(result.role()).isEqualTo(Role.ATHLETE);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    @DisplayName("register - should throw when username already exists")
    void register_shouldThrow_whenUsernameExists() {
        var request = new RegisterRequest("john.doe", "pass", "new@example.com", null);

        given(userRepository.findByUsername("john.doe")).willReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("username");

        verify(userRepository, never()).save(any());
    }

    @Test
    @DisplayName("register - should throw when email already exists")
    void register_shouldThrow_whenEmailExists() {
        var request = new RegisterRequest("new.user", "pass", "john@example.com", null);

        given(userRepository.findByUsername("new.user")).willReturn(Optional.empty());
        given(userRepository.findByEmail("john@example.com")).willReturn(Optional.of(user));

        assertThatThrownBy(() -> authService.register(request))
                .isInstanceOf(ResourceAlreadyExistsException.class)
                .hasMessageContaining("email");

        verify(userRepository, never()).save(any());
    }

    // --- Login ---

    @Test
    @DisplayName("login - should return JWT on valid credentials")
    void login_shouldReturnToken_onValidCredentials() {
        var request = new LoginRequest("john.doe", "password123");

        given(userRepository.findByUsername("john.doe")).willReturn(Optional.of(user));
        given(passwordEncoder.matches("password123", "hashed-password")).willReturn(true);
        given(tokenService.generateToken(user)).willReturn("jwt-token");

        AuthResponse result = authService.login(request);

        assertThat(result.token()).isEqualTo("jwt-token");
    }

    @Test
    @DisplayName("login - should throw BusinessRuleException when user not found")
    void login_shouldThrow_whenUserNotFound() {
        var request = new LoginRequest("unknown", "password");

        given(userRepository.findByUsername("unknown")).willReturn(Optional.empty());

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Invalid username or password");
    }

    @Test
    @DisplayName("login - should throw BusinessRuleException on wrong password")
    void login_shouldThrow_whenPasswordIsWrong() {
        var request = new LoginRequest("john.doe", "wrong-password");

        given(userRepository.findByUsername("john.doe")).willReturn(Optional.of(user));
        given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

        assertThatThrownBy(() -> authService.login(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("Invalid username or password");
    }
}
