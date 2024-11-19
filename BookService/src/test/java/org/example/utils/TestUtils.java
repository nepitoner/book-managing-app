package org.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.Author;
import org.example.entity.Book;

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

    public static BookDtoResponse bookDtoResponse(UUID bookId) {
        return BookDtoResponse.builder()
                .bookId(bookId)
                .title("Beautiful scene")
                .description("Interesting book")
                .isbn("978-0-306-40615-7")
                .genre("Fantasy")
                .authorFirstName("Dag")
                .authorLastName("Filinz")
                .build();
    }

    public static Author expectedAuthor(UUID authorId) {
        return Author.builder().authorId(authorId).firstName("Dag")
                .lastName("Filinz").build();
    }

    public static BookDtoRequest bookDtoRequest(UUID authorId) {
        return BookDtoRequest.builder()
                .title("Beautiful scene")
                .description("Interesting book")
                .isbn("978-0-306-40615-7")
                .genre("Fantasy")
                .authorId(authorId).build();
    }

    public static Book book(UUID bookId, UUID authorId) {
        return Book.builder()
                .title("Beautiful scene")
                .description("Interesting book")
                .isbn("978-0-306-40615-7")
                .genre("Fantasy")
                .author(expectedAuthor(authorId))
                .bookId(bookId)
                .build();
    }

}
