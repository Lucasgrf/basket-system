package com.springboot.projetofinalbackend.DTO;

import com.springboot.projetofinalbackend.model.User;

public record CredentialDTO(
          Long id,
          String photoName,
          String name,
          Long teamId,
          User.Role role
        ) {}
