package com.sporthub.api.service;

import com.sporthub.api.DTO.UserDTO;
import com.sporthub.api.model.User;
import com.sporthub.api.model.enums.Role;
import com.sporthub.api.repository.AthleteRepository;
import com.sporthub.api.repository.TechnicianRepository;
import com.sporthub.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * TODO (Phase 3): Delete this god-service. Admin operations will be handled by
 * role-based @PreAuthorize on existing domain services (AthleteService, TechnicianService, TeamService).
 * Stub retained for compile compatibility with Phase 2 model changes.
 */
@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AthleteRepository athleteRepository;
    private final TechnicianRepository technicianRepository;

    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    public static UserDTO getUserDTO(User user) {
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

    private UserDTO toDTO(User user) {
        return getUserDTO(user);
    }
}
