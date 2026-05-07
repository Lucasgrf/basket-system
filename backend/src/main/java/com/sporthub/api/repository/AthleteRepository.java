package com.sporthub.api.repository;

import com.sporthub.api.model.Athlete;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AthleteRepository extends JpaRepository<Athlete, Long> {
    Optional<Athlete> findByUserId(Long userId);
    Optional<Athlete> findByNickname(String nickname);
    boolean existsByNickname(String nickname);
}
