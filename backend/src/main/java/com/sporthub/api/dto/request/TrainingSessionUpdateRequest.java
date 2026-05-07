package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;

public record TrainingSessionUpdateRequest(
        String title,
        LocalDateTime scheduledAt,
        String location,
        String description,
        Integer durationMinutes,
        SessionStatus status,
        List<Long> confirmedAthleteIds
) {}
