package com.sporthub.api.controller;

import com.sporthub.api.dto.request.TrainingSessionCreateRequest;
import com.sporthub.api.dto.request.TrainingSessionUpdateRequest;
import com.sporthub.api.dto.response.TrainingSessionResponse;
import com.sporthub.api.service.TrainingSessionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/training-sessions")
@RequiredArgsConstructor
public class TrainingSessionController {

    private final TrainingSessionService trainingSessionService;

    @GetMapping
    public ResponseEntity<List<TrainingSessionResponse>> getAllTrainingSessions() {
        return ResponseEntity.ok(trainingSessionService.getAllTrainingSessions());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainingSessionResponse> getTrainingSessionById(@PathVariable Long id) {
        return ResponseEntity.ok(trainingSessionService.getTrainingSessionById(id));
    }

    @PostMapping
    public ResponseEntity<TrainingSessionResponse> createTrainingSession(@RequestBody @Valid TrainingSessionCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(trainingSessionService.createTrainingSession(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainingSessionResponse> updateTrainingSession(@PathVariable Long id, @RequestBody TrainingSessionUpdateRequest request) {
        return ResponseEntity.ok(trainingSessionService.updateTrainingSession(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainingSession(@PathVariable Long id) {
        trainingSessionService.deleteTrainingSession(id);
        return ResponseEntity.noContent().build();
    }
}
