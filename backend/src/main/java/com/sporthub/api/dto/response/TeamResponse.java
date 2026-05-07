package com.sporthub.api.dto.response;

import com.sporthub.api.model.enums.SportType;
import java.time.LocalDate;
import java.util.List;

public record TeamResponse(
        Long id,
        String name,
        String address,
        String gym,
        LocalDate foundation,
        String emailContact,
        String phoneContact,
        SportType sportType,
        Long headTechnicianId,
        String headTechnicianName,
        List<AthleteResponse> athletes
) {}
