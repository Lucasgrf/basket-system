package com.sporthub.api.controller;

import com.sporthub.api.dto.request.LoginRequest;
import com.sporthub.api.dto.request.RegisterRequest;
import com.sporthub.api.dto.response.AuthResponse;
import com.sporthub.api.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Login and registration endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", description = "Authenticate with username and password, returns a JWT token")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest body) {
        return ResponseEntity.ok(authService.login(body));
    }

    @PostMapping("/register")
    @Operation(summary = "Register", description = "Register a new user account")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest body) {
        return ResponseEntity.ok(authService.register(body));
    }
}
