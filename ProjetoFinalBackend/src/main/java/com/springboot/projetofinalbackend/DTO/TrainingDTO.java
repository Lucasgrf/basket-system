package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record TrainingDTO(
        Long id,
        @NotBlank String title,
        @NotNull LocalDateTime dateTime,
        @NotBlank String location,
        @NotNull List<@NotNull Long> credentialIds,
        @NotNull Long teamId
) { }
