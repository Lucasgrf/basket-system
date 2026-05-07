package com.sporthub.api.controller;

import com.sporthub.api.dto.request.TrainingSessionCreateRequest;
import com.sporthub.api.dto.request.TrainingSessionUpdateRequest;
import com.sporthub.api.dto.response.TrainingSessionResponse;
import com.sporthub.api.service.TrainingSessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-sessions")
@RequiredArgsConstructor
@Tag(name = "Training Sessions", description = "Training session scheduling endpoints")
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "List training sessions")
    public ResponseEntity<List<TrainingSessionResponse>> getAllTrainingSessions() {
        return ResponseEntity.ok(trainingSessionService.getAllTrainingSessions());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get training session by ID")
    public ResponseEntity<TrainingSessionResponse> getTrainingSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingSessionService.getTrainingSessionById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @Operation(summary = "Create training session", description = "Schedules a new training session for a team")
    public ResponseEntity<TrainingSessionResponse> createTrainingSession(@RequestBody @Valid TrainingSessionCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingSessionService.createTrainingSession(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @Operation(summary = "Update training session")
    public ResponseEntity<TrainingSessionResponse> updateTrainingSession(@PathVariable Long id, @RequestBody TrainingSessionUpdateRequest request) {
        return ResponseEntity.ok(trainingSessionService.updateTrainingSession(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete training session")
    public ResponseEntity<Void> deleteTrainingSession(@PathVariable Long id) {
        trainingSessionService.deleteTrainingSession(id);
        return ResponseEntity.noContent().build();
    }
}
