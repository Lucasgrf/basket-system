package com.sporthub.api.controller;

import com.sporthub.api.dto.request.AthleteCreateRequest;
import com.sporthub.api.dto.request.AthleteUpdateRequest;
import com.sporthub.api.dto.response.AthleteResponse;
import com.sporthub.api.service.AthleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/athletes")
@RequiredArgsConstructor
public class AthleteController {

    private final AthleteService athleteService;

    @GetMapping
    public ResponseEntity<List<AthleteResponse>> getAllAthletes() {
        return ResponseEntity.ok(athleteService.getAllAthletes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AthleteResponse> getAthleteById(@PathVariable Long id) {
        return ResponseEntity.ok(athleteService.getAthleteById(id));
    }

    @PostMapping
    public ResponseEntity<AthleteResponse> createAthlete(@RequestBody @Valid AthleteCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.createAthlete(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AthleteResponse> updateAthlete(@PathVariable Long id, @RequestBody AthleteUpdateRequest request) {
        return ResponseEntity.ok(athleteService.updateAthlete(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.noContent().build();
    }
}
