package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SessionStatus;
import java.time.LocalDateTime;
import java.util.List;

public record TrainingSessionUpdateRequest(
        LocalDateTime date,
        String local,
        String description,
        Integer durationMinutes,
        SessionStatus status,
        List<Long> confirmedAthleteIds
) {}
