package com.springboot.projetofinalbackend.DTO;

import com.springboot.projetofinalbackend.model.User;

public record RegisterRequestDTO(String username,String email,String password,User.Role role) {
}
