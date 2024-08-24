package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findById(Long id);
}
