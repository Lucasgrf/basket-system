package com.springboot.projetofinalbackend.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        Long id,
        String username,
        @NotBlank String password,
        @Email @NotBlank String email,
        String fullName,
        String photoName,
        UserRole role
) {
    public enum UserRole {
        COACH,
        PLAYER,
        ADMIN
    }
}
