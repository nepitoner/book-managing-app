package org.example.mapper;

import org.example.dto.AuthorDtoRequest;
import org.example.entity.Author;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.example.utils.TestUtils.expectedAuthor;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthorMapperTest {
    @InjectMocks AuthorMapperImpl mapper;

    @Test
    @DisplayName("Test mapping request tot entity")
    void mapRequestToEntityTest() {
        UUID authorId = UUID.randomUUID();
        Author expectedAuthor = expectedAuthor(authorId);

        Author author = mapper.mapRequestToEntity(AuthorDtoRequest.builder().firstName("Dag")
                .lastName("Filinz").build());

        assertEquals(expectedAuthor.getFirstName(), author.getFirstName());
        assertEquals(expectedAuthor.getLastName(), author.getLastName());
    }
}
