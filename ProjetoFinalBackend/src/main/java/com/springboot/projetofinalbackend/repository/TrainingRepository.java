package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

public interface TrainingRepository extends JpaRepository<Training, Long> {
    Optional<Training> findByTitle(String title);
    Optional<Training> findByDate(Date date);
}
