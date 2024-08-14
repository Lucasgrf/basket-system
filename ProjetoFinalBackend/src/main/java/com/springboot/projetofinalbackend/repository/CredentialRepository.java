package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Credential;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CredentialRepository extends JpaRepository<Credential, Long> {
}
