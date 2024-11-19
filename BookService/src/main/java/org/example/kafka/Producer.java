package org.example.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BookIdResponse;
import org.example.entity.Outbox;
import org.example.repository.OutboxRepository;
import org.example.service.KafkaMessagingService;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class Producer {
    private final OutboxRepository outboxRepository;

    private final KafkaMessagingService messagingService;

    @Scheduled(fixedDelay = 60000)
    public void forwardEventsToKafka() {
        int BATCH_SIZE = 5;
        List<Outbox> entities = outboxRepository.findAllByOrderByOutboxIdAsc(Pageable.ofSize(BATCH_SIZE)).toList();
        if (!entities.isEmpty()) {
            for (Outbox entity : entities) {
                messagingService.sendMessage(BookIdResponse.builder().id(entity.getBookId()).build());
            }
            outboxRepository.deleteAllInBatch(entities);
            log.info("Outboxes {} were successfully sent", entities);
        }
    }
}
