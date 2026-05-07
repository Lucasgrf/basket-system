package com.sporthub.api.DTO;

import com.sporthub.api.model.enums.Role;

public record ResponseDTO(Long id, String token, Role role) {
}
