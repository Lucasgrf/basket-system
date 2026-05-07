package com.sporthub.api.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CoachDTO(
        Long id,
        String nickname,
        Long userId,
        Long teamId) {
}
