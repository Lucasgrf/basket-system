package com.sporthub.api.dto.response;

import com.sporthub.api.model.enums.Role;

public record AuthResponse(
        Long id,
        String token,
        Role role
) {}
