package com.springboot.projetofinalbackend.controller;

import com.springboot.projetofinalbackend.DTO.LoginRequestDTO;
import com.springboot.projetofinalbackend.DTO.RegisterRequestDTO;
import com.springboot.projetofinalbackend.DTO.ResponseDTO;
import com.springboot.projetofinalbackend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
   private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDTO> registerUser(@RequestBody RegisterRequestDTO body) {
        return authService.registerUser(body);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDTO> login(@RequestBody LoginRequestDTO body) {
        return authService.login(body);
    }
}
