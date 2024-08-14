package com.springboot.projetofinalbackend.repository;

import com.springboot.projetofinalbackend.model.Training;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingRepository extends JpaRepository<Training, Long> {
}
