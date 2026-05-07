package com.sporthub.api.DTO;

import com.sporthub.api.model.enums.Role;

public record RegisterRequestDTO(String username, String email, String password, Role role) {
}
