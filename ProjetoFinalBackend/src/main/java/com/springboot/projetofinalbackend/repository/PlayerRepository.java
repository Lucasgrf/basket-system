package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByNickname(String nickname);
    Optional<Player> findByUserId(Long userId);
}
