package org.example.service;

import org.example.dto.AuthorDtoRequest;

import java.util.UUID;

public interface AuthorService {
    UUID createAuthor(AuthorDtoRequest authorDtoRequest);
}
