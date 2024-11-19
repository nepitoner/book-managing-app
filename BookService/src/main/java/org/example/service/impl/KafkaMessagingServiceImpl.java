package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.config.KafkaTopicConfigProperties;
import org.example.dto.BookIdResponse;
import org.example.service.KafkaMessagingService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(KafkaTopicConfigProperties.class)
public class KafkaMessagingServiceImpl implements KafkaMessagingService {
    private final KafkaTopicConfigProperties properties;

    private final KafkaTemplate<String, UUID> kafkaTemplate;
    @Override
    public void sendMessage(BookIdResponse message) {
        kafkaTemplate.send(properties.topic().getLibraryTopic(), properties.groupId(), message.id());
        log.info("Message {} was successfully sent", message);
    }
}
