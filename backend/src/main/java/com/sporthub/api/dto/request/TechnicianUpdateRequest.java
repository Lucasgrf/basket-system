package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SportType;
import com.sporthub.api.model.enums.TechnicianRole;

public record TechnicianUpdateRequest(
        String nickname,
        String specialization,
        String licenseNumber,
        TechnicianRole technicianRole,
        SportType sportType,
        Long teamId
) {}
