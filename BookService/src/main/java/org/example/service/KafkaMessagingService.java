package org.example.service;

import org.example.dto.BookIdResponse;

public interface KafkaMessagingService {
    void sendMessage(BookIdResponse message);
}
