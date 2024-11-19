package org.example.mapper;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.LibraryBook;
import org.example.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.TestUtils.availableBook;
import static org.example.utils.TestUtils.bookDtoResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class LibraryBookMapperTest {
    @InjectMocks
    LibraryBookMapperImpl mapper;

    private UUID bookId;

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test mapping entity to response")
    void mapEntityToResponseTest() {
        UUID bookId = UUID.randomUUID();
        UUID libraryBookId = UUID.randomUUID();
        LibraryBook libraryBook = availableBook(bookId, libraryBookId);
        BookDtoResponse expectedBookDtoResponse = BookDtoResponse.builder()
                .libraryBookId(libraryBook.getLibraryBookId())
                .bookId(bookId)
                .isAvailable(true)
                .build();

        BookDtoResponse actualBookDtoResponse = mapper.mapEntityToResponse(libraryBook);

        assertThat(actualBookDtoResponse).usingRecursiveComparison().isEqualTo(expectedBookDtoResponse);
    }

    @Test
    @DisplayName("Test mapping entity to response with total amount")
    void mapEntityToResponse() {
        int totalPageAmount = 5;
        LibraryBook book = availableBook(bookId);

        BookDtoResponse actualBookDtoResponse = mapper.mapEntityToResponse(book, totalPageAmount);

        assertThat(actualBookDtoResponse).usingRecursiveComparison().isEqualTo(bookDtoResponse(bookId));
    }

    @Test
    @DisplayName("Test mapping request to entity")
    void mapRequestToEntity() {
        LibraryBook expectedBook = LibraryBook.builder()
                .isAvailable(false)
                .checkoutDate(LocalDateTime.of(2024, 10, 31, 12, 32))
                .returnDate(LocalDateTime.of(2024, 11, 30, 12, 32)).build();
        BookDtoRequest bookDtoRequest = TestUtils.bookDtoRequest();

        LibraryBook actualBook = mapper.mapRequestToEntity(bookDtoRequest);

        assertEquals(expectedBook.getIsAvailable(), actualBook.getIsAvailable());
        assertEquals(expectedBook.getCheckoutDate(), actualBook.getCheckoutDate());
        assertEquals(expectedBook.getReturnDate(), actualBook.getReturnDate());
    }
}
