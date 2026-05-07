package com.sporthub.api.service;

import com.sporthub.api.dto.request.TeamCreateRequest;
import com.sporthub.api.dto.request.TeamUpdateRequest;
import com.sporthub.api.dto.response.TeamResponse;
import com.sporthub.api.exception.ResourceAlreadyExistsException;
import com.sporthub.api.exception.ResourceNotFoundException;
import com.sporthub.api.mapper.TeamMapper;
import com.sporthub.api.model.Team;
import com.sporthub.api.model.Technician;
import com.sporthub.api.repository.TeamRepository;
import com.sporthub.api.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;
    private final TechnicianRepository technicianRepository;
    private final TeamMapper teamMapper;

    @Transactional(readOnly = true)
    public List<TeamResponse> getAllTeams() {
        return teamRepository.findAll().stream()
                .map(teamMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TeamResponse getTeamById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));
        return teamMapper.toResponse(team);
    }

    @Transactional
    public TeamResponse createTeam(TeamCreateRequest request) {
        // Here we could add a check if team name already exists if needed
        
        Team team = teamMapper.toEntity(request);

        if (request.headTechnicianId() != null) {
            Technician technician = technicianRepository.findById(request.headTechnicianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Technician", "id", request.headTechnicianId()));
            team.setHeadTechnician(technician);
        }

        Team savedTeam = teamRepository.save(team);
        return teamMapper.toResponse(savedTeam);
    }

    @Transactional
    public TeamResponse updateTeam(Long id, TeamUpdateRequest request) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team", "id", id));

        teamMapper.updateEntity(team, request);

        if (request.headTechnicianId() != null) {
            Technician technician = technicianRepository.findById(request.headTechnicianId())
                    .orElseThrow(() -> new ResourceNotFoundException("Technician", "id", request.headTechnicianId()));
            team.setHeadTechnician(technician);
        }

        Team updatedTeam = teamRepository.save(team);
        return teamMapper.toResponse(updatedTeam);
    }

    @Transactional
    public void deleteTeam(Long id) {
        if (!teamRepository.existsById(id)) {
            throw new ResourceNotFoundException("Team", "id", id);
        }
        teamRepository.deleteById(id);
    }
}
