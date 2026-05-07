package com.sporthub.api.mapper;

import com.sporthub.api.dto.request.TrainingSessionCreateRequest;
import com.sporthub.api.dto.request.TrainingSessionUpdateRequest;
import com.sporthub.api.dto.response.AthleteResponse;
import com.sporthub.api.dto.response.TrainingSessionResponse;
import com.sporthub.api.model.TrainingSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TrainingSessionMapper {

    private final AthleteMapper athleteMapper;

    public TrainingSessionResponse toResponse(TrainingSession session) {
        if (session == null) {
            return null;
        }

        Long teamId = session.getTeam() != null ? session.getTeam().getId() : null;
        String teamName = session.getTeam() != null ? session.getTeam().getName() : null;

        List<AthleteResponse> confirmedAthletes = session.getConfirmedAthletes() != null
                ? session.getConfirmedAthletes().stream().map(athleteMapper::toResponse).collect(Collectors.toList())
                : Collections.emptyList();

        return new TrainingSessionResponse(
                session.getId(),
                session.getScheduledAt(),
                session.getLocation(),
                session.getDescription(),
                session.getDurationMinutes(),
                session.getStatus(),
                teamId,
                teamName,
                confirmedAthletes
        );
    }

    public TrainingSession toEntity(TrainingSessionCreateRequest request) {
        if (request == null) {
            return null;
        }

        return TrainingSession.builder()
                .scheduledAt(request.date())
                .location(request.local())
                .description(request.description())
                .durationMinutes(request.durationMinutes())
                .status(request.status())
                .build();
    }

    public void updateEntity(TrainingSession session, TrainingSessionUpdateRequest request) {
        if (request.date() != null) session.setScheduledAt(request.date());
        if (request.local() != null) session.setLocation(request.local());
        if (request.description() != null) session.setDescription(request.description());
        if (request.durationMinutes() != null) session.setDurationMinutes(request.durationMinutes());
        if (request.status() != null) session.setStatus(request.status());
    }
}
