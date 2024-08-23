package com.springboot.projetofinalbackend.DTO;

import com.springboot.projetofinalbackend.model.User;

public record ResponseDTO(Long id, String token, User.Role role) {
}
