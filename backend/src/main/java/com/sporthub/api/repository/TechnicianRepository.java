package com.sporthub.api.repository;

import com.sporthub.api.model.Technician;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnicianRepository extends JpaRepository<Technician, Long> {
    Optional<Technician> findByUserId(Long userId);
    Optional<Technician> findByNickname(String nickname);
}
