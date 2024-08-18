package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.List;

public record TrainingDTO(
        String title,
        LocalDateTime dateTime,
        String location,
        Long teamId
) { }
