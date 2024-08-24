package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
    Optional<Credential> findByName(String name);
    Optional<Credential> findByUserId(Long id);
}
