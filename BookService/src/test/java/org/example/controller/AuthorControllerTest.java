package org.example.controller;

import org.example.dto.AuthorDtoRequest;
import org.example.service.AuthorService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.example.utils.TestUtils.asJson;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {
    @MockBean
    AuthorService authorService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Test creating an author")
    void createAuthorTest() throws Exception {
        UUID authorId = UUID.randomUUID();
        AuthorDtoRequest authorDtoRequest = AuthorDtoRequest.builder()
                .firstName("Dag")
                .lastName("Dadget")
                .build();

        when(authorService.createAuthor(authorDtoRequest)).thenReturn(authorId);

        mockMvc.perform(post("/api/authors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJson(authorDtoRequest)))
                .andExpectAll(status().isCreated(),
                        jsonPath("$.id").value(authorId.toString()));
    }
}
