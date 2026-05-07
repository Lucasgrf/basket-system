package com.sporthub.api.service;

import com.sporthub.api.dto.request.TeamCreateRequest;
import com.sporthub.api.dto.request.TeamUpdateRequest;
import com.sporthub.api.dto.response.TeamResponse;
import com.sporthub.api.exception.ResourceNotFoundException;
import com.sporthub.api.mapper.TeamMapper;
import com.sporthub.api.model.Team;
import com.sporthub.api.model.enums.SportType;
import com.sporthub.api.repository.TeamRepository;
import com.sporthub.api.repository.TechnicianRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TeamService Unit Tests")
class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;
    @Mock
    private TechnicianRepository technicianRepository;
    @Mock
    private TeamMapper teamMapper;

    @InjectMocks
    private TeamService teamService;

    private Team team;
    private TeamResponse teamResponse;

    @BeforeEach
    void setUp() {
        team = Team.builder()
                .id(1L)
                .name("Thunder Hawks")
                .address("123 Court St")
                .gym("Main Arena")
                .foundation(LocalDate.of(2010, 1, 1))
                .emailContact("contact@thunderhawks.com")
                .phoneContact("+55 11 99999-0000")
                .sportType(SportType.BASKETBALL)
                .build();

        teamResponse = new TeamResponse(
                1L, "Thunder Hawks", "123 Court St", "Main Arena",
                LocalDate.of(2010, 1, 1), "contact@thunderhawks.com",
                "+55 11 99999-0000", SportType.BASKETBALL, null, null, List.of()
        );
    }

    @Test
    @DisplayName("getAllTeams - should return paginated results")
    void getAllTeams_shouldReturnPage() {
        var pageable = PageRequest.of(0, 20);
        Page<Team> teamPage = new PageImpl<>(List.of(team));

        given(teamRepository.findAll(pageable)).willReturn(teamPage);
        given(teamMapper.toResponse(team)).willReturn(teamResponse);

        Page<TeamResponse> result = teamService.getAllTeams(pageable);

        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).name()).isEqualTo("Thunder Hawks");
    }

    @Test
    @DisplayName("getTeamById - should return team when found")
    void getTeamById_shouldReturnTeam_whenFound() {
        given(teamRepository.findById(1L)).willReturn(Optional.of(team));
        given(teamMapper.toResponse(team)).willReturn(teamResponse);

        TeamResponse result = teamService.getTeamById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.name()).isEqualTo("Thunder Hawks");
    }

    @Test
    @DisplayName("getTeamById - should throw ResourceNotFoundException when not found")
    void getTeamById_shouldThrow_whenNotFound() {
        given(teamRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.getTeamById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Team");
    }

    @Test
    @DisplayName("createTeam - should persist and return new team")
    void createTeam_shouldSaveAndReturn() {
        var request = new TeamCreateRequest(
                "Thunder Hawks", "123 Court St", "Main Arena",
                LocalDate.of(2010, 1, 1), "contact@thunderhawks.com",
                "+55 11 99999-0000", SportType.BASKETBALL, null
        );

        given(teamMapper.toEntity(request)).willReturn(team);
        given(teamRepository.save(team)).willReturn(team);
        given(teamMapper.toResponse(team)).willReturn(teamResponse);

        TeamResponse result = teamService.createTeam(request);

        assertThat(result.name()).isEqualTo("Thunder Hawks");
        assertThat(result.sportType()).isEqualTo(SportType.BASKETBALL);
        verify(teamRepository, times(1)).save(team);
    }

    @Test
    @DisplayName("createTeam - should throw when headTechnician not found")
    void createTeam_shouldThrow_whenTechnicianNotFound() {
        var request = new TeamCreateRequest(
                "Thunder Hawks", "123 Court St", "Main Arena",
                LocalDate.of(2010, 1, 1), "contact@thunderhawks.com",
                "+55 11 99999-0000", SportType.BASKETBALL, 42L
        );

        given(teamMapper.toEntity(request)).willReturn(team);
        given(technicianRepository.findById(42L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.createTeam(request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Technician");

        verify(teamRepository, never()).save(any());
    }

    @Test
    @DisplayName("updateTeam - should throw ResourceNotFoundException when team not found")
    void updateTeam_shouldThrow_whenTeamNotFound() {
        var request = new TeamUpdateRequest("New Name", null, null, null, null, null, null, null);

        given(teamRepository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> teamService.updateTeam(99L, request))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessageContaining("Team");
    }

    @Test
    @DisplayName("deleteTeam - should call deleteById when team exists")
    void deleteTeam_shouldDeleteSuccessfully() {
        given(teamRepository.existsById(1L)).willReturn(true);

        teamService.deleteTeam(1L);

        verify(teamRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("deleteTeam - should throw when team does not exist")
    void deleteTeam_shouldThrow_whenNotFound() {
        given(teamRepository.existsById(99L)).willReturn(false);

        assertThatThrownBy(() -> teamService.deleteTeam(99L))
                .isInstanceOf(ResourceNotFoundException.class);

        verify(teamRepository, never()).deleteById(any());
    }
}
