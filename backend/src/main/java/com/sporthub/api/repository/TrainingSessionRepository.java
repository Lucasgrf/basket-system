package com.sporthub.api.repository;

import com.sporthub.api.model.TrainingSession;
import com.sporthub.api.model.enums.SessionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {
    List<TrainingSession> findByTeamId(Long teamId);
    List<TrainingSession> findByTeamIdAndStatus(Long teamId, SessionStatus status);
}
