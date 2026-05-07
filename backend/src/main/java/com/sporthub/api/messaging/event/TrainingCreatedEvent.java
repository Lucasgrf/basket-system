package com.sporthub.api.messaging.event;

import java.time.LocalDateTime;

/**
 * Published when a new training session is scheduled.
 */
public record TrainingCreatedEvent(
        Long sessionId,
        String title,
        Long teamId,
        String teamName,
        LocalDateTime scheduledAt,
        String location
) {}
