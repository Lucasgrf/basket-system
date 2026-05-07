package com.sporthub.api.service;

import com.sporthub.api.model.Technician;
import com.sporthub.api.repository.TechnicianRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * TODO (Phase 3): Replace with TechnicianService. CoachService is removed.
 * Stub retained for compile compatibility — CoachController still references this.
 */
@Service
@RequiredArgsConstructor
public class CoachService {

    private final TechnicianRepository technicianRepository;

    public ResponseEntity<List<Technician>> getAll() {
        return ResponseEntity.ok(technicianRepository.findAll());
    }
}
