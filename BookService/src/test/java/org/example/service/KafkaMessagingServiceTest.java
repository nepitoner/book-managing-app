package org.example.service;

import org.example.dto.BookIdResponse;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.annotation.DirtiesContext;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = {"port=9092"})
class KafkaMessagingTest {
    @Autowired
    KafkaMessagingService service;

    @Test
    @DisplayName("Test sending message to kafka")
    void sendMessageTest() {
        BookIdResponse bookIdResponse = BookIdResponse.builder().id(UUID.randomUUID()).build();

        service.sendMessage(bookIdResponse);
    }
}
