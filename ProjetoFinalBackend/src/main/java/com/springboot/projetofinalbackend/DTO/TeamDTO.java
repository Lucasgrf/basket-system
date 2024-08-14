package com.springboot.projetofinalbackend.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.List;


public record TeamDTO(
        Long id,
        @NotBlank String name,
        @NotBlank String address,
        @NotBlank String gym,
        @NotNull Date foundation,
        @NotBlank String emailContact,
        @NotBlank String phoneContact,
        @NotNull Long coachId,
        @NotNull List<PlayerDTO> players
) { }
