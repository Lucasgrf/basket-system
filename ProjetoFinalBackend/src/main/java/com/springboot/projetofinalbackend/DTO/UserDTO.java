package com.springboot.projetofinalbackend.DTO;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
        @NotBlank String username,
        @NotBlank String password,
        @Email @NotBlank String email,
        String photoName
) {}
