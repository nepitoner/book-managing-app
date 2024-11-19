package org.example.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter(AccessLevel.PUBLIC)
@Table(name = LibraryBook.TABLE_NAME)
public class LibraryBook {
    public static final String TABLE_NAME = "library_books";

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "library_book_id", updatable = false, nullable = false)
    private UUID libraryBookId;

    @Column(name = "book_id", updatable = false, nullable = false)
    private UUID bookId;

    @Column(name = "is_available", nullable = false)
    private Boolean isAvailable;

    @Column(name = "checkout_date")
    private LocalDateTime checkoutDate;

    @Column(name = "return_date")
    private LocalDateTime returnDate;
}
