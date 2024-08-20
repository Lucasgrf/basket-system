package com.springboot.projetofinalbackend.DTO;


import com.springboot.projetofinalbackend.model.User;

public record UserDTO(
        Long id,
        String username,
        String email,
        String photoName,
        User.Role role,
        Long playerId,
        Long coachId,
        Long credentialId) {
}
