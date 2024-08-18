package com.springboot.projetofinalbackend.DTO;


import com.springboot.projetofinalbackend.model.User;

public record UserDTO(
        String username,
        String password,
        String email,
        String photoName,
        User.Role role
) {}
