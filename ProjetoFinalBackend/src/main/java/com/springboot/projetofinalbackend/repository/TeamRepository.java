package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
