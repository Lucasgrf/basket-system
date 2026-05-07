package com.sporthub.api.messaging.consumer;

import com.sporthub.api.messaging.config.RabbitMQConfig;
import com.sporthub.api.messaging.event.AthleteJoinedTeamEvent;
import com.sporthub.api.messaging.event.TrainingCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationConsumer {

    @RabbitListener(queues = RabbitMQConfig.QUEUE_TRAINING_CREATED)
    public void onTrainingCreated(TrainingCreatedEvent event) {
        // In a production system this would send push notifications, emails, etc.
        log.info("[NOTIFICATION] New training session '{}' scheduled at {} in {} for team '{}'",
                event.title(),
                event.scheduledAt(),
                event.location(),
                event.teamName());
    }

    @RabbitListener(queues = RabbitMQConfig.QUEUE_ATHLETE_JOINED)
    public void onAthleteJoinedTeam(AthleteJoinedTeamEvent event) {
        log.info("[NOTIFICATION] Athlete '{}' joined team '{}' ({} sport)",
                event.athleteUsername(),
                event.teamName(),
                event.sportType());
    }
}
