package com.sporthub.api.messaging.producer;

import com.sporthub.api.messaging.config.RabbitMQConfig;
import com.sporthub.api.messaging.event.AthleteJoinedTeamEvent;
import com.sporthub.api.messaging.event.TrainingCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishTrainingCreated(TrainingCreatedEvent event) {
        log.info("Publishing TrainingCreatedEvent: sessionId={}, team={}", event.sessionId(), event.teamName());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_TRAINING_CREATED,
                event
        );
    }

    public void publishAthleteJoinedTeam(AthleteJoinedTeamEvent event) {
        log.info("Publishing AthleteJoinedTeamEvent: athlete={}, team={}", event.athleteUsername(), event.teamName());
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_ATHLETE_JOINED,
                event
        );
    }
}
