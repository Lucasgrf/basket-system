package com.sporthub.api.service;

import com.sporthub.api.dto.request.TechnicianCreateRequest;
import com.sporthub.api.dto.request.TechnicianUpdateRequest;
import com.sporthub.api.dto.response.TechnicianResponse;
import com.sporthub.api.exception.BusinessRuleException;
import com.sporthub.api.exception.ResourceNotFoundException;
import com.sporthub.api.mapper.TechnicianMapper;
import com.sporthub.api.model.Team;
import com.sporthub.api.model.Technician;
import com.sporthub.api.model.User;
import com.sporthub.api.repository.TeamRepository;
import com.sporthub.api.repository.TechnicianRepository;
import com.sporthub.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TechnicianService {

    private final TechnicianRepository technicianRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final TechnicianMapper technicianMapper;

    @Transactional(readOnly = true)
    public List<TechnicianResponse> getAllTechnicians() {
        return technicianRepository.findAll().stream()
                .map(technicianMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public TechnicianResponse getTechnicianById(Long id) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technician", "id", id));
        return technicianMapper.toResponse(technician);
    }

    @Transactional
    public TechnicianResponse createTechnician(TechnicianCreateRequest request) {
        Technician technician = technicianMapper.toEntity(request);

        if (request.userId() != null) {
            User user = userRepository.findById(request.userId())
                    .orElseThrow(() -> new ResourceNotFoundException("User", "id", request.userId()));
            if (user.getTechnician() != null) {
                throw new BusinessRuleException("User already has an associated technician profile");
            }
            technician.setUser(user);
        }

        if (request.teamId() != null) {
            Team team = teamRepository.findById(request.teamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team", "id", request.teamId()));
            
            if (team.getSportType() != request.sportType()) {
                throw new BusinessRuleException("Technician sport type does not match team sport type");
            }
            
            technician.setTeam(team);
        }

        Technician savedTechnician = technicianRepository.save(technician);
        
        // Ensure bidirectional relationship if user is attached
        if (savedTechnician.getUser() != null) {
            savedTechnician.getUser().setTechnician(savedTechnician);
            userRepository.save(savedTechnician.getUser());
        }
        
        return technicianMapper.toResponse(savedTechnician);
    }

    @Transactional
    public TechnicianResponse updateTechnician(Long id, TechnicianUpdateRequest request) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technician", "id", id));

        technicianMapper.updateEntity(technician, request);

        if (request.teamId() != null) {
            Team team = teamRepository.findById(request.teamId())
                    .orElseThrow(() -> new ResourceNotFoundException("Team", "id", request.teamId()));
            
            if (team.getSportType() != technician.getSportType()) {
                throw new BusinessRuleException("Technician sport type does not match team sport type");
            }
            
            technician.setTeam(team);
        }

        Technician updatedTechnician = technicianRepository.save(technician);
        return technicianMapper.toResponse(updatedTechnician);
    }

    @Transactional
    public void deleteTechnician(Long id) {
        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Technician", "id", id));
                
        // Cleanup bidirectional relationship
        if (technician.getUser() != null) {
            technician.getUser().setTechnician(null);
            userRepository.save(technician.getUser());
        }
                
        technicianRepository.delete(technician);
    }
}
