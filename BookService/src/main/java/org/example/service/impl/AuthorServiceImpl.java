package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.AuthorDtoRequest;
import org.example.mapper.AuthorMapper;
import org.example.repository.AuthorRepository;
import org.example.service.AuthorService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    private final AuthorMapper authorMapper;

    @Override
    public UUID createAuthor(AuthorDtoRequest authorDtoRequest) {
        UUID authorId = authorRepository.save(authorMapper.mapRequestToEntity(authorDtoRequest)).getAuthorId();
        log.info("Author with name {} {} was successfully created. New id {}",
                authorDtoRequest.firstName(), authorDtoRequest.lastName(), authorId);
        return authorId;
    }
}
