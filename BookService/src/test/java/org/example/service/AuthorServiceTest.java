package org.example.service;

import org.example.dto.AuthorDtoRequest;
import org.example.entity.Author;
import org.example.mapper.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.example.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.example.utils.TestUtils.expectedAuthor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {
    @Mock
    AuthorRepository authorRepository;

    @Mock
    AuthorMapper mapper;

    @InjectMocks
    AuthorServiceImpl authorService;

    @Test
    @DisplayName("Test creating author with correct data")
    void createAuthorTest() {
        UUID expectedAuthorId = UUID.randomUUID();
        Author expectedAuthor = expectedAuthor(null);
        AuthorDtoRequest authorDtoRequest = AuthorDtoRequest.builder()
                .firstName("Dag")
                .lastName("Filinz").build();

        when(mapper.mapRequestToEntity(authorDtoRequest)).thenReturn(expectedAuthor);
        when(authorRepository.save(expectedAuthor)).thenReturn(expectedAuthor(expectedAuthorId));


        UUID actualAuthorId = authorService.createAuthor(authorDtoRequest);

        assertEquals(expectedAuthorId, actualAuthorId);
        verify(mapper).mapRequestToEntity(authorDtoRequest);
        verify(authorRepository).save(expectedAuthor);
    }
}
