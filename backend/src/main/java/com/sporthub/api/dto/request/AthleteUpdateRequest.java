package com.sporthub.api.dto.request;

import java.time.LocalDate;

public record AthleteUpdateRequest(
        String nickname,
        LocalDate birthDate,
        Long teamId,

        // Sport-specific fields
        String position,
        Double height,
        Double weight,
        String preferredFoot,
        Integer shirtNumber,
        Double reachHeight
) {}
