package com.sporthub.api.service;

import com.sporthub.api.model.Team;
import com.sporthub.api.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

/**
 * TODO (Phase 3): Fully rewrite. TeamService will use TeamCreateRequest/TeamResponse DTOs,
 * TeamMapper, and throw typed exceptions instead of returning ResponseEntity.
 * Stub retained for compile compatibility with Phase 2 model changes.
 */
@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public ResponseEntity<List<Team>> getAll() {
        return ResponseEntity.ok(teamRepository.findAll());
    }

    public ResponseEntity<Team> getById(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        return ResponseEntity.ok(team);
    }

    public ResponseEntity<Void> delete(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));
        teamRepository.delete(team);
        return ResponseEntity.noContent().build();
    }
}
