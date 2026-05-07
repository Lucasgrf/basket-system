package com.sporthub.api.service;

import com.sporthub.api.dto.request.TechnicianCreateRequest;
import com.sporthub.api.dto.response.TechnicianResponse;
import com.sporthub.api.exception.BusinessRuleException;
import com.sporthub.api.exception.ResourceNotFoundException;
import com.sporthub.api.mapper.TechnicianMapper;
import com.sporthub.api.model.Team;
import com.sporthub.api.model.Technician;
import com.sporthub.api.model.User;
import com.sporthub.api.model.enums.Role;
import com.sporthub.api.model.enums.SportType;
import com.sporthub.api.model.enums.TechnicianRole;
import com.sporthub.api.repository.TeamRepository;
import com.sporthub.api.repository.TechnicianRepository;
import com.sporthub.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TechnicianService Unit Tests")
class TechnicianServiceTest {

    @Mock private TechnicianRepository technicianRepository;
    @Mock private TeamRepository teamRepository;
    @Mock private UserRepository userRepository;
    @Mock private TechnicianMapper technicianMapper;

    @InjectMocks private TechnicianService technicianService;

    private User user;
    private Technician technician;
    private TechnicianResponse technicianResponse;
    private Team team;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("coach.silva");
        user.setRole(Role.TECHNICIAN);

        team = Team.builder()
                .id(10L)
                .name("Thunder Hawks")
                .sportType(SportType.BASKETBALL)
                .build();

        technician = new Technician();
        technician.setId(1L);
        technician.setUser(user);
        technician.setTeam(team);

        technicianResponse = new TechnicianResponse(
                1L, "coach.silva", "Conditioning", "LIC-001",
                TechnicianRole.HEAD_COACH, SportType.BASKETBALL,
                1L, null, 10L, "Thunder Hawks"
        );
    }

    @Test
    @DisplayName("getAllTechnicians - should return a list")
    void getAllTechnicians_shouldReturnList() {
        given(technicianRepository.findAll()).willReturn(List.of(technician));
        given(technicianMapper.toResponse(technician)).willReturn(technicianResponse);

        var result = technicianService.getAllTechnicians();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).nickname()).isEqualTo("coach.silva");
    }

    @Test
    @DisplayName("getTechnicianById - should throw when not found")
    void getTechnicianById_shouldThrow_whenNotFound() {
        given(technicianRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> technicianService.getTechnicianById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Technician");
    }

    @Test
    @DisplayName("createTechnician - should throw BusinessRuleException when user already has a technician profile")
    void createTechnician_shouldThrow_whenUserAlreadyHasProfile() {
        var request = new TechnicianCreateRequest(
                "coach.silva", "Conditioning", "LIC-001",
                TechnicianRole.HEAD_COACH, SportType.BASKETBALL, 1L, null
        );

        user.setTechnician(technician); // simulate existing profile
        given(technicianMapper.toEntity(request)).willReturn(new Technician());
        given(userRepository.findById(1L)).willReturn(Optional.of(user));

        assertThatThrownBy(() -> technicianService.createTechnician(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("already has an associated technician profile");

        verify(technicianRepository, never()).save(any());
    }

    @Test
    @DisplayName("createTechnician - should throw when sport types mismatch between technician and team")
    void createTechnician_shouldThrow_whenSportTypeMismatch() {
        // request is for SOCCER but team is BASKETBALL
        var request = new TechnicianCreateRequest(
                "coach.silva", "Conditioning", "LIC-001",
                TechnicianRole.HEAD_COACH, SportType.SOCCER, null, 10L
        );

        given(technicianMapper.toEntity(request)).willReturn(technician);
        given(teamRepository.findById(10L)).willReturn(Optional.of(team));

        assertThatThrownBy(() -> technicianService.createTechnician(request))
                .isInstanceOf(BusinessRuleException.class)
                .hasMessageContaining("sport type");

        verify(technicianRepository, never()).save(any());
    }

    @Test
    @DisplayName("deleteTechnician - should throw when not found")
    void deleteTechnician_shouldThrow_whenNotFound() {
        given(technicianRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> technicianService.deleteTechnician(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(technicianRepository, never()).delete(any());
    }
}
