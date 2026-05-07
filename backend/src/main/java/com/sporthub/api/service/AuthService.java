package com.sporthub.api.service;

import com.sporthub.api.dto.request.LoginRequest;
import com.sporthub.api.dto.request.RegisterRequest;
import com.sporthub.api.dto.response.AuthResponse;
import com.sporthub.api.exception.BusinessRuleException;
import com.sporthub.api.exception.ResourceAlreadyExistsException;
import com.sporthub.api.model.User;
import com.sporthub.api.repository.UserRepository;
import com.sporthub.api.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "username", request.username());
        }

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResourceAlreadyExistsException("User", "email", request.email());
        }

        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole(request.role());
        user.setPhotoUrl(request.photoUrl());

        User savedUser = userRepository.save(user);
        String token = tokenService.generateToken(savedUser);

        return new AuthResponse(savedUser.getId(), token, savedUser.getRole());
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.username())
                .orElseThrow(() -> new BusinessRuleException("Invalid username or password"));

        if (!passwordEncoder.matches(request.password(), user.getPassword())) {
            throw new BusinessRuleException("Invalid username or password");
        }

        String token = tokenService.generateToken(user);
        return new AuthResponse(user.getId(), token, user.getRole());
    }
}
