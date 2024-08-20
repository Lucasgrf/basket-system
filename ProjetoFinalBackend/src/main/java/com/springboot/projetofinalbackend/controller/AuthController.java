package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.LoginRequestDTO;
import com.springboot.projetofinalbackend.DTO.RegisterRequestDTO;
import com.springboot.projetofinalbackend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
   private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody RegisterRequestDTO body) {
        return authService.registerUser(body);
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequestDTO body) {
        return authService.login(body);
    }
}
