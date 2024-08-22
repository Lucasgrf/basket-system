package com.springboot.projetofinalbackend.DTO;

import com.springboot.projetofinalbackend.model.User;

public record ResponseDTO(String name, String token, User.Role role) {
}
