package com.sporthub.api;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Full application context smoke test.
 *
 * Requires a running PostgreSQL instance and RabbitMQ to load successfully.
 * This test is intentionally @Disabled for the unit/slice test suite.
 * It should be executed only in a full integration environment (e.g., via docker-compose).
 *
 * To run manually: remove @Disabled and start docker-compose up -d before executing.
 */
@SpringBootTest
@Disabled("Requires full infrastructure: PostgreSQL + RabbitMQ. Run with docker-compose.")
class SportHubApiApplicationTests {

    @Test
    void contextLoads() {
        // Verifies the full Spring ApplicationContext starts without errors.
    }
}
