package com.sporthub.api.DTO;

import com.sporthub.api.model.User;

public record ResponseDTO(Long id, String token, User.Role role) {
}
