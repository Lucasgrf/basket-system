package com.sporthub.api.DTO;

public record CredentialDTO(
        Long id,
        String photoName,
        String name,
        Long teamId,
        String userType,
        Long userId) {
}
