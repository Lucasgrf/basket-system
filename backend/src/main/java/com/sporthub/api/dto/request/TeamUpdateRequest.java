package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SportType;
import java.time.LocalDate;

public record TeamUpdateRequest(
        String name,
        String address,
        String gym,
        LocalDate foundation,
        String emailContact,
        String phoneContact,
        SportType sportType,
        Long headTechnicianId
) {}
