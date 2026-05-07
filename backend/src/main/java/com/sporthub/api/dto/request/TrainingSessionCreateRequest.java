package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SessionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record TrainingSessionCreateRequest(
        @NotNull(message = "Date is required")
        LocalDateTime date,

        @NotBlank(message = "Local is required")
        String local,

        String description,
        Integer durationMinutes,

        @NotNull(message = "Status is required")
        SessionStatus status,

        @NotNull(message = "Team ID is required")
        Long teamId,

        List<Long> confirmedAthleteIds
) {}
