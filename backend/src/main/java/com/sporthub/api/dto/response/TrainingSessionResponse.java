package com.sporthub.api.dto.response;

import com.sporthub.api.model.enums.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;

public record TrainingSessionResponse(
        Long id,
        LocalDateTime date,
        String local,
        String description,
        Integer durationMinutes,
        SessionStatus status,
        Long teamId,
        String teamName,
        List<AthleteResponse> confirmedAthletes
) {}
