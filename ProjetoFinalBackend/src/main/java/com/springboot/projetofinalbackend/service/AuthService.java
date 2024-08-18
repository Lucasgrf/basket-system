package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.LoginRequestDTO;
import com.springboot.projetofinalbackend.DTO.RegisterRequestDTO;
import com.springboot.projetofinalbackend.DTO.ResponseDTO;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.UserRepository;
import com.springboot.projetofinalbackend.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private TokenService tokenService;

    public ResponseEntity registerUser(@Valid RegisterRequestDTO body) {
        Optional<User> existingUser = userRepository.findByEmail(body.email());

        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setUsername(body.username());
            newUser.setRole(body.role());

            if(newUser.getRole().equals("PLAYER")) {

            }

            userRepository.save(newUser);
            credentialService.create(newUser);
            String token = tokenService.generateToken(newUser);

            return ResponseEntity.ok(new ResponseDTO(newUser.getUsername(), token));
        }

        return ResponseEntity.badRequest().build();
    }


    public ResponseEntity login(@Valid LoginRequestDTO body) {
        var user = userRepository.findByEmail(body.email()).orElseThrow(() -> new RuntimeException("User not found."));
        if (authenticate(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public boolean authenticate(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
