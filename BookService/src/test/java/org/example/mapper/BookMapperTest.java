package org.example.mapper;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.Book;
import org.example.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.TestUtils.book;
import static org.example.utils.TestUtils.bookDtoResponse;
import static org.example.utils.TestUtils.expectedAuthor;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BookMapperTest {
    @InjectMocks
    BookMapperImpl mapper;

    private UUID authorId;
    private UUID bookId;

    @BeforeEach
    void setUp() {
        authorId = UUID.randomUUID();
        bookId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test mapping entity to response")
    void mapEntityToResponseTest() {
        Book book = TestUtils.book(bookId, authorId);

        BookDtoResponse actualBookDtoResponse = mapper.mapEntityToResponse(book);

        assertThat(actualBookDtoResponse).usingRecursiveComparison().isEqualTo(bookDtoResponse(bookId));
    }

    @Test
    @DisplayName("Test mapping entity to response with total amount")
    void mapEntityToResponse() {
        int totalPageAmount = 5;
        Book book = TestUtils.book(bookId, authorId);

        BookDtoResponse actualBookDtoResponse = mapper.mapEntityToResponse(book, totalPageAmount);

        assertThat(actualBookDtoResponse).usingRecursiveComparison().isEqualTo(bookDtoResponse(bookId));
    }

    @Test
    @DisplayName("Test mapping request to entity")
    void mapRequestToEntity() {
        Book expectedBook = book(bookId, authorId);
        BookDtoRequest bookDtoRequest = TestUtils.bookDtoRequest(authorId);

        Book actualBook = mapper.mapRequestToEntity(bookDtoRequest, expectedAuthor(authorId));

        assertEquals(expectedBook.getTitle(), actualBook.getTitle());
        assertEquals(expectedBook.getIsbn(), actualBook.getIsbn());
        assertEquals(expectedBook.getGenre(), actualBook.getGenre());
        assertEquals(expectedBook.getAuthor().getAuthorId(), actualBook.getAuthor().getAuthorId());
        assertEquals(expectedBook.getDescription(), actualBook.getDescription());
    }
}
