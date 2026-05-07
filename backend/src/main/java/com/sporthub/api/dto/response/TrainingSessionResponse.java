package com.sporthub.api.dto.response;

import com.sporthub.api.model.enums.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;

public record TrainingSessionResponse(
        Long id,
        String title,
        LocalDateTime scheduledAt,
        String location,
        String description,
        Integer durationMinutes,
        SessionStatus status,
        Long teamId,
        String teamName,
        List<AthleteResponse> confirmedAthletes
) {}
