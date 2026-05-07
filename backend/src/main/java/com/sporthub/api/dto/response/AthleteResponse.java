package com.sporthub.api.dto.response;

import com.sporthub.api.model.enums.SportType;
import java.time.LocalDate;

public record AthleteResponse(
        Long id,
        String nickname,
        LocalDate birthDate,
        SportType sportType,
        Long userId,
        String photoUrl,
        Long teamId,
        String teamName,

        // Sport-specific fields
        String position,
        Double height,
        Double weight,
        String preferredFoot,
        Integer shirtNumber,
        Double reachHeight
) {}
