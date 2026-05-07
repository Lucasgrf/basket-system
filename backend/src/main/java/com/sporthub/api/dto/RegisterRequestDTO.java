package com.sporthub.api.DTO;

import com.sporthub.api.model.User;

public record RegisterRequestDTO(String username,String email,String password,User.Role role) {
}
