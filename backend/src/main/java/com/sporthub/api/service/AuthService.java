package com.sporthub.api.service;

import com.sporthub.api.DTO.LoginRequestDTO;
import com.sporthub.api.DTO.RegisterRequestDTO;
import com.sporthub.api.DTO.ResponseDTO;
import com.sporthub.api.model.Technician;
import com.sporthub.api.model.User;
import com.sporthub.api.model.enums.Role;
import com.sporthub.api.repository.TechnicianRepository;
import com.sporthub.api.repository.UserRepository;
import com.sporthub.api.security.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * TODO (Phase 3): Fully rewrite. Stub retained for compile compatibility with Phase 2 model changes.
 * AuthService will be refactored to: remove ResponseEntity, use proper exceptions,
 * and route registration to AthleteService/TechnicianService.
 */
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final TechnicianRepository technicianRepository;

    public ResponseEntity<ResponseDTO> registerUser(RegisterRequestDTO body) {
        Optional<User> existingUser = userRepository.findByEmail(body.email());

        if (existingUser.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        User newUser = User.builder()
                .password(passwordEncoder.encode(body.password()))
                .email(body.email())
                .username(body.username())
                .role(body.role())
                .build();

        userRepository.save(newUser);

        if (body.role() == Role.TECHNICIAN) {
            Technician technician = Technician.builder()
                    .user(newUser)
                    .build();
            technicianRepository.save(technician);
        }
        // Athlete registration (with sport profile) will be handled in Phase 3 via AthleteService

        String token = tokenService.generateToken(newUser);
        return ResponseEntity.ok(new ResponseDTO(newUser.getId(), token, newUser.getRole()));
    }

    public ResponseEntity<ResponseDTO> login(LoginRequestDTO body) {
        User user = userRepository.findByEmail(body.email())
                .orElseThrow(() -> new RuntimeException("User not found."));
        if (passwordEncoder.matches(body.password(), user.getPassword())) {
            String token = tokenService.generateToken(user);
            return ResponseEntity.ok(new ResponseDTO(user.getId(), token, user.getRole()));
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }
}
