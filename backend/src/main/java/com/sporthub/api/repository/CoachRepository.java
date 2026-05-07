package com.sporthub.api.repository;

import com.sporthub.api.model.Coach;
import com.sporthub.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<Coach, Long> {
    Optional<Coach> findById(Long id);
    Optional<Coach> findByNickname(String nickname);
    Optional<Coach> findByUserId(Long userId);
}
