package com.sporthub.api.controller;

import com.sporthub.api.dto.request.TeamCreateRequest;
import com.sporthub.api.dto.request.TeamUpdateRequest;
import com.sporthub.api.dto.response.TeamResponse;
import com.sporthub.api.service.TeamService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
@Tag(name = "Teams", description = "Team management endpoints")
public class TeamController {

    private final TeamService teamService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "List teams", description = "Returns a paginated list of all teams")
    public ResponseEntity<Page<TeamResponse>> getAllTeams(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        var pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(teamService.getAllTeams(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get team by ID")
    public ResponseEntity<TeamResponse> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create team", description = "Creates a new team for a given sport")
    public ResponseEntity<TeamResponse> createTeam(@RequestBody @Valid TeamCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(teamService.createTeam(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @Operation(summary = "Update team")
    public ResponseEntity<TeamResponse> updateTeam(@PathVariable Long id, @RequestBody TeamUpdateRequest request) {
        return ResponseEntity.ok(teamService.updateTeam(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete team")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }
}
