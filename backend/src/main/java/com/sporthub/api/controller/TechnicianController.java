package com.sporthub.api.controller;

import com.sporthub.api.dto.request.TechnicianCreateRequest;
import com.sporthub.api.dto.request.TechnicianUpdateRequest;
import com.sporthub.api.dto.response.TechnicianResponse;
import com.sporthub.api.service.TechnicianService;
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
@RequestMapping("/technicians")
@RequiredArgsConstructor
@Tag(name = "Technicians", description = "Technician (coach/staff) management endpoints")
public class TechnicianController {

    private final TechnicianService technicianService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "List technicians")
    public ResponseEntity<List<TechnicianResponse>> getAllTechnicians() {
        return ResponseEntity.ok(technicianService.getAllTechnicians());
    }

    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get technician by ID")
    public ResponseEntity<TechnicianResponse> getTechnicianById(@PathVariable Long id) {
        return ResponseEntity.ok(technicianService.getTechnicianById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create technician", description = "Registers a new technician (admin only)")
    public ResponseEntity<TechnicianResponse> createTechnician(@RequestBody @Valid TechnicianCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(technicianService.createTechnician(request));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TECHNICIAN')")
    @Operation(summary = "Update technician")
    public ResponseEntity<TechnicianResponse> updateTechnician(@PathVariable Long id, @RequestBody TechnicianUpdateRequest request) {
        return ResponseEntity.ok(technicianService.updateTechnician(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete technician")
    public ResponseEntity<Void> deleteTechnician(@PathVariable Long id) {
        technicianService.deleteTechnician(id);
        return ResponseEntity.noContent().build();
    }
}
