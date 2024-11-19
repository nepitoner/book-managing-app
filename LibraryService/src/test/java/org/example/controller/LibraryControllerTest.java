package org.example.controller;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.service.LibraryService;
import org.example.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.example.utils.TestUtils.asJson;
import static org.example.utils.TestUtils.bookDtoResponse;
import static org.example.utils.TestUtils.unavailableBookDtoResponse;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(LibraryController.class)
public class LibraryControllerTest {
    @MockBean
    LibraryService libraryService;

    @Autowired
    MockMvc mockMvc;

    private UUID bookId;

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test getting all books that are available")
    void getAllAvailableBooks() throws Exception {
        int page = 1;
        int limit = 10;
        List<BookDtoResponse> bookDtoResponseList = List.of(bookDtoResponse(bookId));

        when(libraryService.getAllAvailableBooks(page, limit)).thenReturn(bookDtoResponseList);

        mockMvc.perform(get("/api/library")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$[0].bookId").value(bookId.toString()),
                        jsonPath("$[0].isAvailable").value(Boolean.toString(true))
                );
    }

    @Test
    @DisplayName("Test updating the book")
    void updateBookTest() throws Exception {
        BookDtoRequest bookDtoRequest = TestUtils.bookDtoRequest();

        when(libraryService.updateBook(bookId, bookDtoRequest)).thenReturn(unavailableBookDtoResponse(bookId));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

        mockMvc.perform(put("/api/library/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(bookDtoRequest)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.bookId").value(bookId.toString()),
                        jsonPath("$.isAvailable").value(bookDtoRequest.isAvailable()),
                        jsonPath("$.checkoutDate").value(bookDtoRequest.checkoutDate().format(formatter)),
                        jsonPath("$.returnDate").value(bookDtoRequest.returnDate().format(formatter))
                );
    }
}
