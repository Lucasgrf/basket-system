package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SessionStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

public record TrainingSessionCreateRequest(
        @NotBlank(message = "Title is required")
        String title,

        @NotNull(message = "Date is required")
        LocalDateTime scheduledAt,

        @NotBlank(message = "Location is required")
        String location,

        String description,
        Integer durationMinutes,

        @NotNull(message = "Status is required")
        SessionStatus status,

        @NotNull(message = "Team ID is required")
        Long teamId,

        List<Long> confirmedAthleteIds
) {}
