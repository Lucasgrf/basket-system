package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SportType;
import com.sporthub.api.model.enums.TechnicianRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TechnicianCreateRequest(
        @NotBlank(message = "Nickname is required")
        String nickname,

        String specialization,
        String licenseNumber,

        @NotNull(message = "Technician role is required")
        TechnicianRole technicianRole,

        @NotNull(message = "Sport type is required")
        SportType sportType,

        @NotNull(message = "User ID is required")
        Long userId,
        Long teamId
) {}
