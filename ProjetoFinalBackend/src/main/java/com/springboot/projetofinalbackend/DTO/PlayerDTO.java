package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlayerDTO(
        @NotNull String position,
        @Positive double height,
        @Positive double weight,
        @Positive int age,
        Long teamId,
        @NotNull Long userId
) { }
