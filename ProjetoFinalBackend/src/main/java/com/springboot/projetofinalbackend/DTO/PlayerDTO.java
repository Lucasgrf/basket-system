package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlayerDTO(
        Long id,
        @NotBlank String position,
        @Positive double height,
        @Positive double weight,
        @Positive int age,
        @NotNull Long teamId,
        @NotNull Long userId
) { }
