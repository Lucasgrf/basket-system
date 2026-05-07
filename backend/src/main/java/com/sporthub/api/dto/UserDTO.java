package com.sporthub.api.DTO;

import com.sporthub.api.model.enums.Role;

public record UserDTO(
        Long id,
        String username,
        String email,
        String photoName,
        Role role,
        Long athleteId,
        Long technicianId) {
}
