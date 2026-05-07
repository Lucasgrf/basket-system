package com.sporthub.api.dto.response;

import com.sporthub.api.model.enums.Role;

public record UserResponse(
        Long id,
        String username,
        String email,
        String photoUrl,
        Role role,
        Long athleteId,
        Long technicianId
) {}
