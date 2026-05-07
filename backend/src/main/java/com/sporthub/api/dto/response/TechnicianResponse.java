package com.sporthub.api.dto.response;

import com.sporthub.api.model.enums.SportType;
import com.sporthub.api.model.enums.TechnicianRole;

public record TechnicianResponse(
        Long id,
        String nickname,
        String specialization,
        String licenseNumber,
        TechnicianRole technicianRole,
        SportType sportType,
        Long userId,
        String photoUrl,
        Long teamId,
        String teamName
) {}
