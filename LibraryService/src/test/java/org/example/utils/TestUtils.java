package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.dto.BookIdResponse;
import org.example.entity.LibraryBook;

import java.time.LocalDateTime;
import java.util.UUID;

public final class TestUtils {
    private TestUtils() {
    }

    private static final ObjectMapper MAPPER = new ObjectMapper()
            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
            .registerModule(new JavaTimeModule());

    public static String asJson(Object object) throws JsonProcessingException {
        return MAPPER.writeValueAsString(object);
    }

    public static LibraryBook availableBook(UUID bookId) {
        return LibraryBook.builder().isAvailable(true).bookId(bookId).build();
    }

    public static LibraryBook availableBook(UUID bookId, UUID libraryBookId) {
        return LibraryBook.builder().libraryBookId(libraryBookId)
                .isAvailable(true).bookId(bookId).build();
    }

    public static BookDtoResponse bookDtoResponse(UUID bookId) {
        return BookDtoResponse.builder()
                .bookId(bookId)
                .isAvailable(true)
                .build();
    }

    public static BookDtoRequest bookDtoRequest() {
        return BookDtoRequest.builder()
                .isAvailable(false)
                .checkoutDate(LocalDateTime.of(2024, 10, 31, 12, 32))
                .returnDate(LocalDateTime.of(2024, 11, 30, 12, 32))
                .build();
    }

    public static BookDtoResponse unavailableBookDtoResponse(UUID bookId) {
        return BookDtoResponse.builder()
                .bookId(bookId)
                .isAvailable(false)
                .checkoutDate(LocalDateTime.of(2024, 10, 31, 12, 32))
                .returnDate(LocalDateTime.of(2024, 11, 30, 12, 32))
                .build();
    }

    public static BookIdResponse bookIdResponse(UUID bookId) {
        return BookIdResponse.builder().bookId(bookId).build();
    }
}
