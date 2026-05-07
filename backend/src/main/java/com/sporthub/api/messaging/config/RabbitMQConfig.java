package com.sporthub.api.messaging.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Exchange — one topic exchange for all SportHub domain events
    public static final String EXCHANGE = "sporthub.events";

    // Queues
    public static final String QUEUE_TRAINING_CREATED  = "sporthub.training.created";
    public static final String QUEUE_ATHLETE_JOINED    = "sporthub.athlete.joined";

    // Routing keys
    public static final String ROUTING_TRAINING_CREATED = "training.created";
    public static final String ROUTING_ATHLETE_JOINED   = "athlete.joined";

    @Bean
    public TopicExchange sportHubExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public Queue trainingCreatedQueue() {
        return QueueBuilder.durable(QUEUE_TRAINING_CREATED).build();
    }

    @Bean
    public Queue athleteJoinedQueue() {
        return QueueBuilder.durable(QUEUE_ATHLETE_JOINED).build();
    }

    @Bean
    public Binding trainingCreatedBinding() {
        return BindingBuilder
                .bind(trainingCreatedQueue())
                .to(sportHubExchange())
                .with(ROUTING_TRAINING_CREATED);
    }

    @Bean
    public Binding athleteJoinedBinding() {
        return BindingBuilder
                .bind(athleteJoinedQueue())
                .to(sportHubExchange())
                .with(ROUTING_ATHLETE_JOINED);
    }

    /** JSON serialization for messages */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        var template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
