package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PlayerDTO(
        String position,
        double height,
        double weight,
        int age,
        Long teamId,
        Long userId
) { }
