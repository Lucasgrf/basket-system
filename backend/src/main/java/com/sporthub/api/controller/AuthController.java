package com.sporthub.api.controller;

import com.sporthub.api.dto.request.LoginRequest;
import com.sporthub.api.dto.request.RegisterRequest;
import com.sporthub.api.dto.response.AuthResponse;
import com.sporthub.api.service.AuthService;
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
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid LoginRequest body) {
        return ResponseEntity.ok(authService.login(body));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid RegisterRequest body) {
        return ResponseEntity.ok(authService.register(body));
    }
}
