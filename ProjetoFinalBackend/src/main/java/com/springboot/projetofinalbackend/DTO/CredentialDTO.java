package com.springboot.projetofinalbackend.DTO;

import com.springboot.projetofinalbackend.model.User;

public record CredentialDTO(
          String photoName,
          String name,
          Long teamId,
          User.Role role
        ) {}
