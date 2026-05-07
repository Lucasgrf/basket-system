package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record AthleteCreateRequest(
        @NotBlank(message = "Nickname is required")
        String nickname,

        @NotNull(message = "Birth date is required")
        LocalDate birthDate,

        @NotNull(message = "Sport type is required")
        SportType sportType,

        @NotNull(message = "User ID is required")
        Long userId,
        Long teamId,

        // Sport-specific fields (optional based on sportType)
        String position,
        Double height,
        Double weight,
        String preferredFoot,
        Integer shirtNumber,
        Double reachHeight
) {}
