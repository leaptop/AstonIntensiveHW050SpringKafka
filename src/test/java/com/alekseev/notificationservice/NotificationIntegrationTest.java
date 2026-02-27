package com.alekseev.notificationservice;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import jakarta.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
@Testcontainers
class NotificationIntegrationTest {

    @Container
    private static final KafkaContainer kafkaContainer = new KafkaContainer(
            DockerImageName.parse("confluentinc/cp-kafka:7.6.0")
    );

    private GreenMail greenMail;

    @Autowired
    private KafkaTemplate<String, Map<String, Object>> kafkaTemplate;

    @DynamicPropertySource
    static void overrideProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.kafka.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }

    @BeforeEach
    void setUp() {
        greenMail = new GreenMail(new ServerSetup(2525, null, "smtp"));
        greenMail.start();
    }

    @AfterEach
    void tearDown() {
        greenMail.stop();
    }

    @Test
    void shouldSendEmailOnCreateEvent() throws Exception {
        Map<String, Object> message = new HashMap<>();
        message.put("operation", "create");
        message.put("email", "test@example.com");

        kafkaTemplate.send("user-events", message);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
            assertThat(receivedMessages).hasSize(1);
            assertThat(receivedMessages[0].getSubject()).isEqualTo("Account Notification");
            assertThat((String) receivedMessages[0].getContent()).contains("успешно создан");
        });
    }

    @Test
    void shouldSendEmailOnDeleteEvent() throws Exception {
        Map<String, Object> message = new HashMap<>();
        message.put("operation", "delete");
        message.put("email", "test@example.com");

        kafkaTemplate.send("user-events", message);

        await().atMost(5, TimeUnit.SECONDS).untilAsserted(() -> {
            MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
            assertThat(receivedMessages).hasSize(1);
            assertThat(receivedMessages[0].getSubject()).isEqualTo("Account Notification");
            assertThat((String) receivedMessages[0].getContent()).contains("удалён");
        });
    }

    @Test
    void shouldSendEmailViaApi() throws Exception {
        // Тест для REST API
        // Здесь можно добавить тест для NotificationController
    }
}