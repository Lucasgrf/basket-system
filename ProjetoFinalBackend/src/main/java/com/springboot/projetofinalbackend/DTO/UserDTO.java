package com.springboot.projetofinalbackend.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserDTO(
        @NotNull Long id,
        @NotBlank String username,
        @NotBlank String password,
        @Email @NotBlank String email,
        String fullName,
        String photoName,
        @NotBlank UserRole role
) {
    public enum UserRole {
        COACH,
        PLAYER
    }
}
