package org.example.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.service.LibraryService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class Listener {
    private final LibraryService libraryService;

    @KafkaListener(topics = "book-library-service-topic", groupId = "library")
    public void onMessage(UUID id) {
        log.info("Information about book {} successfully obtained", id);
        libraryService.saveNewBook(id);
    }
}
