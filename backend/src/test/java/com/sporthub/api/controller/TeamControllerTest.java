package com.sporthub.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sporthub.api.dto.request.TeamCreateRequest;
import com.sporthub.api.dto.response.TeamResponse;
import com.sporthub.api.model.enums.SportType;
import com.sporthub.api.repository.UserRepository;
import com.sporthub.api.security.CustomUserDetailsService;
import com.sporthub.api.security.SecurityConfig;
import com.sporthub.api.security.TokenService;
import com.sporthub.api.service.TeamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @WebMvcTest slice for TeamController with real SecurityConfig.
 *
 * - @Import(SecurityConfig) brings the real filter chain, @PreAuthorize, and security DSL.
 * - @WithMockUser injects a test principal directly into the SecurityContext.
 *   SecurityFilter skips the JWT check (no Authorization header) and just calls chain.doFilter.
 *   Spring Security then uses the @WithMockUser principal for @PreAuthorize.
 * - TokenService, UserRepository, CustomUserDetailsService are mocked to satisfy
 *   the SecurityFilter and SecurityConfig bean dependencies without hitting the DB.
 */
@WebMvcTest(TeamController.class)
@Import(SecurityConfig.class)
@DisplayName("TeamController Integration Tests (MockMvc)")
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TeamService teamService;

    // Dependencies required by SecurityFilter and SecurityConfig
    @MockBean
    private TokenService tokenService;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private CustomUserDetailsService customUserDetailsService;

    private TeamResponse buildTeamResponse() {
        return new TeamResponse(
                1L, "Thunder Hawks", "123 Court St", "Main Arena",
                LocalDate.of(2010, 1, 1), "contact@thunderhawks.com",
                "+55 11 99999-0000", SportType.BASKETBALL, null, null, List.of()
        );
    }

    // ---- GET /teams ----

    @Test
    @DisplayName("GET /teams - should return 200 for authenticated user")
    @WithMockUser(roles = "ATHLETE")
    void getAllTeams_shouldReturn200_whenAuthenticated() throws Exception {
        Page<TeamResponse> page = new PageImpl<>(List.of(buildTeamResponse()));
        given(teamService.getAllTeams(any(Pageable.class))).willReturn(page);

        mockMvc.perform(get("/teams"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name", is("Thunder Hawks")))
                .andExpect(jsonPath("$.content[0].sportType", is("BASKETBALL")));
    }

    @Test
    @DisplayName("GET /teams - should return 403 when unauthenticated (no credentials)")
    void getAllTeams_shouldReturn403_whenUnauthenticated() throws Exception {
        mockMvc.perform(get("/teams"))
                .andExpect(status().isForbidden());
    }

    // ---- POST /teams ----

    @Test
    @DisplayName("POST /teams - should return 201 when called by ADMIN")
    @WithMockUser(roles = "ADMIN")
    void createTeam_shouldReturn201_whenAdmin() throws Exception {
        var request = new TeamCreateRequest(
                "Thunder Hawks", "123 Court St", "Main Arena",
                LocalDate.of(2010, 1, 1), "contact@thunderhawks.com",
                "+55 11 99999-0000", SportType.BASKETBALL, null
        );

        given(teamService.createTeam(any(TeamCreateRequest.class))).willReturn(buildTeamResponse());

        mockMvc.perform(post("/teams")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Thunder Hawks")));
    }

    @Test
    @DisplayName("POST /teams - should return 403 when called by TECHNICIAN")
    @WithMockUser(roles = "TECHNICIAN")
    void createTeam_shouldReturn403_whenTechnician() throws Exception {
        var request = new TeamCreateRequest(
                "Thunder Hawks", null, null, null, null, null, SportType.BASKETBALL, null
        );

        mockMvc.perform(post("/teams")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST /teams - should return 400 when name is blank")
    @WithMockUser(roles = "ADMIN")
    void createTeam_shouldReturn400_whenNameBlank() throws Exception {
        var request = new TeamCreateRequest(
                "", null, null, null, null, null, SportType.BASKETBALL, null
        );

        mockMvc.perform(post("/teams")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    // ---- DELETE /teams/{id} ----

    @Test
    @DisplayName("DELETE /teams/{id} - should return 204 when called by ADMIN")
    @WithMockUser(roles = "ADMIN")
    void deleteTeam_shouldReturn204_whenAdmin() throws Exception {
        mockMvc.perform(delete("/teams/1").with(csrf()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("DELETE /teams/{id} - should return 403 when called by TECHNICIAN")
    @WithMockUser(roles = "TECHNICIAN")
    void deleteTeam_shouldReturn403_whenTechnician() throws Exception {
        mockMvc.perform(delete("/teams/1").with(csrf()))
                .andExpect(status().isForbidden());
    }
}
