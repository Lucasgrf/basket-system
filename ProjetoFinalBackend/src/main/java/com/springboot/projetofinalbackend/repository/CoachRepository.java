package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<Coach, Long> {
}
