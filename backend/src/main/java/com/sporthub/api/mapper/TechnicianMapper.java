package com.sporthub.api.mapper;

import com.sporthub.api.dto.request.TechnicianCreateRequest;
import com.sporthub.api.dto.request.TechnicianUpdateRequest;
import com.sporthub.api.dto.response.TechnicianResponse;
import com.sporthub.api.model.Technician;
import org.springframework.stereotype.Component;

@Component
public class TechnicianMapper {

    public TechnicianResponse toResponse(Technician technician) {
        if (technician == null) {
            return null;
        }

        Long userId = technician.getUser() != null ? technician.getUser().getId() : null;
        String photoUrl = technician.getUser() != null ? technician.getUser().getPhotoUrl() : null;
        Long teamId = technician.getTeam() != null ? technician.getTeam().getId() : null;
        String teamName = technician.getTeam() != null ? technician.getTeam().getName() : null;

        return new TechnicianResponse(
                technician.getId(),
                technician.getNickname(),
                technician.getSpecialization(),
                technician.getLicenseNumber(),
                technician.getTechnicianRole(),
                technician.getSportType(),
                userId,
                photoUrl,
                teamId,
                teamName
        );
    }

    public Technician toEntity(TechnicianCreateRequest request) {
        if (request == null) {
            return null;
        }

        return Technician.builder()
                .nickname(request.nickname())
                .specialization(request.specialization())
                .licenseNumber(request.licenseNumber())
                .technicianRole(request.technicianRole())
                .sportType(request.sportType())
                .build();
    }

    public void updateEntity(Technician technician, TechnicianUpdateRequest request) {
        if (request.nickname() != null) technician.setNickname(request.nickname());
        if (request.specialization() != null) technician.setSpecialization(request.specialization());
        if (request.licenseNumber() != null) technician.setLicenseNumber(request.licenseNumber());
        if (request.technicianRole() != null) technician.setTechnicianRole(request.technicianRole());
        if (request.sportType() != null) technician.setSportType(request.sportType());
    }
}
