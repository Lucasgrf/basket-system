package com.sporthub.api.mapper;

import com.sporthub.api.dto.response.UserResponse;
import com.sporthub.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        Long athleteId = user.getAthlete() != null ? user.getAthlete().getId() : null;
        Long technicianId = user.getTechnician() != null ? user.getTechnician().getId() : null;

        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPhotoUrl(),
                user.getRole(),
                athleteId,
                technicianId
        );
    }
}
