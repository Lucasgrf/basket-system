package com.sporthub.api.service;

import com.sporthub.api.model.Athlete;
import com.sporthub.api.repository.AthleteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO (Phase 3): Replace with AthleteService. PlayerService is removed.
 * Stub retained for compile compatibility — PlayerController still references this.
 */
@Service
@RequiredArgsConstructor
public class PlayerService {

    private final AthleteRepository athleteRepository;

    public ResponseEntity<List<Athlete>> getAll() {
        return ResponseEntity.ok(athleteRepository.findAll());
    }
}
