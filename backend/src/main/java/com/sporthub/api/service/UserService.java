package com.sporthub.api.service;

import com.sporthub.api.DTO.RequestUpdateUser;
import com.sporthub.api.DTO.UserDTO;
import com.sporthub.api.model.User;
import com.sporthub.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * TODO (Phase 3): Fully rewrite. Stub retained for compile compatibility.
 * Will be refactored to: remove ResponseEntity, use mappers, throw typed exceptions.
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<UserDTO> getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        return ResponseEntity.ok(toDTO(user));
    }

    public ResponseEntity<UserDTO> updateProfile(Long id, RequestUpdateUser request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        if (request.username() != null && !request.username().isBlank()) user.setUsername(request.username());
        if (request.email() != null && !request.email().isBlank()) user.setEmail(request.email());
        if (request.photoName() != null && !request.photoName().isBlank()) user.setPhotoName(request.photoName());
        if (request.password() != null && !request.password().isBlank()) user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);
        return ResponseEntity.ok(toDTO(user));
    }

    public ResponseEntity<Void> deleteProfile(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        userRepository.delete(user);
        return ResponseEntity.noContent().build();
    }

    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhotoName(),
                user.getRole(),
                user.getAthlete() != null ? user.getAthlete().getId() : null,
                user.getTechnician() != null ? user.getTechnician().getId() : null
        );
    }
}
