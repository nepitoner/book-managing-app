package org.example.repository;

import org.example.entity.LibraryBook;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LibraryBookRepository extends JpaRepository<LibraryBook, UUID> {
    Page<LibraryBook> findByIsAvailableTrue(Pageable pageable);

    boolean existsByBookId(UUID bookId);
}
