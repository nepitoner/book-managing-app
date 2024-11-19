package org.example.repository;

import org.example.entity.Outbox;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OutboxRepository extends JpaRepository<Outbox, Integer> {
    Page<Outbox> findAllByOrderByOutboxIdAsc(Pageable pageable);
}
