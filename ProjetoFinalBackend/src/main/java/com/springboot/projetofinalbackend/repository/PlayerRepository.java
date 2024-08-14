package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, Long> {
}
