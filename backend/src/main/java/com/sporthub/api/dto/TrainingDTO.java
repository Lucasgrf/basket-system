package com.sporthub.api.DTO;

import java.util.Date;

public record TrainingDTO(
        Long id,
        String title,
        Date date,
        String location,
        Long teamId) {
}
