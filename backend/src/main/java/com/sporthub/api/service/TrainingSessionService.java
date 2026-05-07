package com.sporthub.api.service;

import com.sporthub.api.dto.request.TrainingSessionCreateRequest;
import com.sporthub.api.dto.request.TrainingSessionUpdateRequest;
import com.sporthub.api.dto.response.TrainingSessionResponse;
import com.sporthub.api.exception.BusinessRuleException;
import com.sporthub.api.exception.ResourceNotFoundException;
import com.sporthub.api.mapper.TrainingSessionMapper;
import com.sporthub.api.model.Athlete;
import com.sporthub.api.model.Team;
import com.sporthub.api.model.TrainingSession;
import com.sporthub.api.repository.AthleteRepository;
import com.sporthub.api.repository.TeamRepository;
import com.sporthub.api.repository.TrainingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingSessionService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final TeamRepository teamRepository;
    private final AthleteRepository athleteRepository;
    private final TrainingSessionMapper trainingSessionMapper;

    @Transactional(readOnly = true)
    public List<TrainingSessionResponse> getAllTrainingSessions() {
        return trainingSessionRepository.findAll().stream()
                .map(trainingSessionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TrainingSessionResponse getTrainingSessionById(Long id) {
        TrainingSession session = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrainingSession", "id", id));
        return trainingSessionMapper.toResponse(session);
    }

    @Transactional
    public TrainingSessionResponse createTrainingSession(TrainingSessionCreateRequest request) {
        TrainingSession session = trainingSessionMapper.toEntity(request);

        Team team = teamRepository.findById(request.teamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", request.teamId()));
        session.setTeam(team);

        if (request.confirmedAthleteIds() != null && !request.confirmedAthleteIds().isEmpty()) {
            Set<Athlete> confirmedAthletes = new HashSet<>();
            for (Long athleteId : request.confirmedAthleteIds()) {
                Athlete athlete = athleteRepository.findById(athleteId)
                        .orElseThrow(() -> new ResourceNotFoundException("Athlete", "id", athleteId));
                
                if (athlete.getTeam() == null || !athlete.getTeam().getId().equals(team.getId())) {
                    throw new BusinessRuleException("Athlete " + athleteId + " does not belong to team " + team.getId());
                }
                
                confirmedAthletes.add(athlete);
            }
            session.setConfirmedAthletes(confirmedAthletes);
        }

        TrainingSession savedSession = trainingSessionRepository.save(session);
        return trainingSessionMapper.toResponse(savedSession);
    }

    @Transactional
    public TrainingSessionResponse updateTrainingSession(Long id, TrainingSessionUpdateRequest request) {
        TrainingSession session = trainingSessionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("TrainingSession", "id", id));

        trainingSessionMapper.updateEntity(session, request);

        if (request.confirmedAthleteIds() != null) {
            Set<Athlete> confirmedAthletes = new HashSet<>();
            for (Long athleteId : request.confirmedAthleteIds()) {
                Athlete athlete = athleteRepository.findById(athleteId)
                        .orElseThrow(() -> new ResourceNotFoundException("Athlete", "id", athleteId));
                
                if (athlete.getTeam() == null || !athlete.getTeam().getId().equals(session.getTeam().getId())) {
                    throw new BusinessRuleException("Athlete " + athleteId + " does not belong to team " + session.getTeam().getId());
                }
                
                confirmedAthletes.add(athlete);
            }
            session.setConfirmedAthletes(confirmedAthletes);
        }

        TrainingSession updatedSession = trainingSessionRepository.save(session);
        return trainingSessionMapper.toResponse(updatedSession);
    }

    @Transactional
    public void deleteTrainingSession(Long id) {
        if (!trainingSessionRepository.existsById(id)) {
            throw new ResourceNotFoundException("TrainingSession", "id", id);
        }
        trainingSessionRepository.deleteById(id);
    }
}
