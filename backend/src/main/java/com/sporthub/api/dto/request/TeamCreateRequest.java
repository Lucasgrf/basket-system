package com.sporthub.api.dto.request;

import com.sporthub.api.model.enums.SportType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record TeamCreateRequest(
        @NotBlank(message = "Name is required")
        String name,

        String address,
        String gym,
        LocalDate foundation,
        String emailContact,
        String phoneContact,

        @NotNull(message = "Sport type is required")
        SportType sportType,

        Long headTechnicianId
) {}
