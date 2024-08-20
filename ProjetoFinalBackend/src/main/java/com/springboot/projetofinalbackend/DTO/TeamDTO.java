package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

public record TeamDTO(
        Long id,
        String name,
        String address,
        String gym,
        Date foundation,
        String emailContact,
        String phoneContact,
        Long coachId) {
}
