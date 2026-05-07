package com.sporthub.api.controller;

import com.sporthub.api.dto.request.AthleteCreateRequest;
import com.sporthub.api.dto.request.AthleteUpdateRequest;
import com.sporthub.api.dto.response.AthleteResponse;
import com.sporthub.api.service.AthleteService;
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
@RequestMapping("/athletes")
@RequiredArgsConstructor
@Tag(name = "Athletes", description = "Athlete management endpoints")
public class AthleteController {

    private final AthleteService athleteService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "List athletes", description = "Returns a paginated list of all athletes")
    public ResponseEntity<Page<AthleteResponse>> getAllAthletes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        var pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return ResponseEntity.ok(athleteService.getAllAthletes(pageable));
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get athlete by ID")
    public ResponseEntity<AthleteResponse> getAthleteById(@PathVariable Long id) {
        return ResponseEntity.ok(athleteService.getAthleteById(id));
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @Operation(summary = "Create athlete", description = "Creates a new athlete profile linked to a user")
    public ResponseEntity<AthleteResponse> createAthlete(@RequestBody @Valid AthleteCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(athleteService.createAthlete(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @Operation(summary = "Update athlete")
    public ResponseEntity<AthleteResponse> updateAthlete(@PathVariable Long id, @RequestBody AthleteUpdateRequest request) {
        return ResponseEntity.ok(athleteService.updateAthlete(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete athlete")
    public ResponseEntity<Void> deleteAthlete(@PathVariable Long id) {
        athleteService.deleteAthlete(id);
        return ResponseEntity.noContent().build();
    }
}
