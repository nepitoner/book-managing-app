package org.example.controller;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.service.BookService;
import org.example.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.example.utils.TestUtils.asJson;
import static org.example.utils.TestUtils.bookDtoResponse;

@WebMvcTest(BookController.class)
public class BookControllerTest {
    @MockBean
    BookService bookService;

    @Autowired
    MockMvc mockMvc;

    private UUID bookId;

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test getting all books")
    void getAllBooksTest() throws Exception {
        int page = 1;
        int limit = 10;
        List<BookDtoResponse> bookDtoResponseList = List.of(bookDtoResponse(bookId));

        when(bookService.getAllBooks(page, limit)).thenReturn(bookDtoResponseList);

        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$[0].bookId").value(bookId.toString()),
                        jsonPath("$[0].title").value("Beautiful scene"),
                        jsonPath("$[0].description").value("Interesting book"),
                        jsonPath("$[0].isbn").value("978-0-306-40615-7"),
                        jsonPath("$[0].genre").value("Fantasy"),
                        jsonPath("$[0].authorFirstName").value("Dag"),
                        jsonPath("$[0].authorLastName").value("Filinz")
                        );
    }

    @Test
    @DisplayName("Test getting the book by a correct id")
    void getBookByIdTest() throws Exception {
        when(bookService.getBookById(bookId)).thenReturn(bookDtoResponse(bookId));

        mockMvc.perform(get("/api/books/{bookId}", bookId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$.bookId").value(bookId.toString()),
                        jsonPath("$.title").value("Beautiful scene"),
                        jsonPath("$.description").value("Interesting book"),
                        jsonPath("$.isbn").value("978-0-306-40615-7"),
                        jsonPath("$.genre").value("Fantasy"),
                        jsonPath("$.authorFirstName").value("Dag"),
                        jsonPath("$.authorLastName").value("Filinz")
                );
    }

    @Test
    @DisplayName("Test getting the book by a correct isbn")
    void getBookByIsbnTest() throws Exception {
        String isbn = "978-0-306-40615-7";
        when(bookService.getBookByIsbn(isbn)).thenReturn(bookDtoResponse(bookId));

        mockMvc.perform(get("/api/books/isbn/{isbn}", isbn)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(status().isOk(),
                        jsonPath("$.bookId").value(bookId.toString()),
                        jsonPath("$.title").value("Beautiful scene"),
                        jsonPath("$.description").value("Interesting book"),
                        jsonPath("$.isbn").value("978-0-306-40615-7"),
                        jsonPath("$.genre").value("Fantasy"),
                        jsonPath("$.authorFirstName").value("Dag"),
                        jsonPath("$.authorLastName").value("Filinz")
                );
    }

    @Test
    @DisplayName("Test creating the book with correct data")
    void createBookTest() throws Exception {
        UUID authorId = UUID.randomUUID();
        BookDtoRequest bookDtoRequest = TestUtils.bookDtoRequest(authorId);

        when(bookService.createBook(bookDtoRequest)).thenReturn(bookId);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(bookDtoRequest)))
                .andExpectAll(status().isCreated(),
                        jsonPath("$.id").value(bookId.toString())
                );
    }

    @Test
    @DisplayName("Test updating the book with correct data")
    void updateBookTest() throws Exception {
        UUID authorId = UUID.randomUUID();
        BookDtoRequest bookDtoRequest = TestUtils.bookDtoRequest(authorId);

        when(bookService.updateBook(bookId, bookDtoRequest)).thenReturn(bookDtoResponse(bookId));

        mockMvc.perform(put("/api/books/{bookId}", bookId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJson(bookDtoRequest)))
                .andExpectAll(status().isOk(),
                        jsonPath("$.bookId").value(bookId.toString()),
                        jsonPath("$.title").value("Beautiful scene"),
                        jsonPath("$.description").value("Interesting book"),
                        jsonPath("$.isbn").value("978-0-306-40615-7"),
                        jsonPath("$.genre").value("Fantasy"),
                        jsonPath("$.authorFirstName").value("Dag"),
                        jsonPath("$.authorLastName").value("Filinz")
                );
    }

    @Test
    @DisplayName("Test deleting the book with correct id")
    void deleteBookTest() throws Exception {
        doNothing().when(bookService).deleteBook(bookId);

        mockMvc.perform(delete("/api/books/{bookId}", bookId))
                .andExpect(status().isNoContent());
    }
}
