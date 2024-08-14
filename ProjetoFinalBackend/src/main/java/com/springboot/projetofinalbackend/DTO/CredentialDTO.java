package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.NotBlank;

public record CredentialDTO(
        Long id,
        String photoName,
        @NotBlank String name,
        Long teamId,
        @NotBlank Long userId
) {}
