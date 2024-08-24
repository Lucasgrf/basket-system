package com.springboot.projetofinalbackend.DTO;

public record CredentialDTO(
        Long id,
        String photoName,
        String name,
        Long teamId,
        String userType,
        Long userId) {
}
