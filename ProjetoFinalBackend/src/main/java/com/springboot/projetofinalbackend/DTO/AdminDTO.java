package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record AdminDTO(
        Long id,
        @NotBlank String username,
        @NotBlank String password,
        @NotBlank @Email String email
) {}
