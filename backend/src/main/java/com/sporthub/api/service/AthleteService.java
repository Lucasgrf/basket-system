package com.sporthub.api.service;

import com.sporthub.api.dto.request.AthleteCreateRequest;
import com.sporthub.api.dto.request.AthleteUpdateRequest;
import com.sporthub.api.dto.response.AthleteResponse;
import com.sporthub.api.exception.BusinessRuleException;
import com.sporthub.api.exception.ResourceNotFoundException;
import com.sporthub.api.mapper.AthleteMapper;
import com.sporthub.api.model.Athlete;
import com.sporthub.api.model.Team;
import com.sporthub.api.model.User;
import com.sporthub.api.repository.AthleteRepository;
import com.sporthub.api.repository.TeamRepository;
import com.sporthub.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AthleteService {

    private final AthleteRepository athleteRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final AthleteMapper athleteMapper;

    @Transactional(readOnly = true)
    public List<AthleteResponse> getAllAthletes() {
        return athleteRepository.findAll().stream()
                .map(athleteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AthleteResponse getAthleteById(Long id) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete", "id", id));
        return athleteMapper.toResponse(athlete);
    }

    @Transactional
    public AthleteResponse createAthlete(AthleteCreateRequest request) {
        Athlete athlete = athleteMapper.toEntity(request);

        if (request.userId() != null) {
            User user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.userId()));
            if (user.getAthlete() != null) {
                throw new BusinessRuleException("User already has an associated athlete profile");
            }
            athlete.setUser(user);
        }

        if (request.teamId() != null) {
            Team team = teamRepository.findById(request.teamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team", "id", request.teamId()));
            
            if (team.getSportType() != request.sportType()) {
                throw new BusinessRuleException("Athlete sport type does not match team sport type");
            }
            
            athlete.setTeam(team);
        }

        Athlete savedAthlete = athleteRepository.save(athlete);
        
        // Ensure bidirectional relationship if user is attached
        if (savedAthlete.getUser() != null) {
            savedAthlete.getUser().setAthlete(savedAthlete);
            userRepository.save(savedAthlete.getUser());
        }
        
        return athleteMapper.toResponse(savedAthlete);
    }

    @Transactional
    public AthleteResponse updateAthlete(Long id, AthleteUpdateRequest request) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete", "id", id));

        athleteMapper.updateEntity(athlete, request);

        if (request.teamId() != null) {
            Team team = teamRepository.findById(request.teamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team", "id", request.teamId()));
            
            // Note: in a real system you might check if the team's sport type matches the athlete's subclass
            
            athlete.setTeam(team);
        }

        Athlete updatedAthlete = athleteRepository.save(athlete);
        return athleteMapper.toResponse(updatedAthlete);
    }

    @Transactional
    public void deleteAthlete(Long id) {
        Athlete athlete = athleteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Athlete", "id", id));
                
        // Cleanup bidirectional relationship
        if (athlete.getUser() != null) {
            athlete.getUser().setAthlete(null);
            userRepository.save(athlete.getUser());
        }
                
        athleteRepository.delete(athlete);
    }
}
