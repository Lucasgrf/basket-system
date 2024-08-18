package com.springboot.projetofinalbackend.service;

import com.springboot.projetofinalbackend.DTO.LoginRequestDTO;
import com.springboot.projetofinalbackend.DTO.RegisterRequestDTO;
import com.springboot.projetofinalbackend.DTO.ResponseDTO;
import com.springboot.projetofinalbackend.model.Coach;
import com.springboot.projetofinalbackend.model.Player;
import com.springboot.projetofinalbackend.model.User;
import com.springboot.projetofinalbackend.repository.CoachRepository;
import com.springboot.projetofinalbackend.repository.PlayerRepository;
import com.springboot.projetofinalbackend.repository.UserRepository;
import com.springboot.projetofinalbackend.security.TokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CredentialService credentialService;
    private final TokenService tokenService;
    private final PlayerRepository playerRepository;
    private final CoachRepository coachRepository;

    public ResponseEntity registerUser(@Valid RegisterRequestDTO body) {
        Optional<User> existingUser = userRepository.findByEmail(body.email());

        if (existingUser.isEmpty()) {
            User newUser = new User();
            newUser.setPassword(passwordEncoder.encode(body.password()));
            newUser.setEmail(body.email());
            newUser.setUsername(body.username());
            newUser.setRole(body.role());

            switch (body.role()) {
                case COACH -> {
                    Coach coach = new Coach();
                    coach.setUser(newUser);
                    coachRepository.save(coach);
                }
                case PLAYER -> {
                    Player player = new Player();
                    player.setUser(newUser);
                    playerRepository.save(player);
                }
                case ADMIN -> {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
                }
                default -> throw new IllegalStateException("Role not found: " + body.role());
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
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getUsername(), token));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    public boolean authenticate(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}