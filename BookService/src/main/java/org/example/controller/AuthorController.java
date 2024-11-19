package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.dto.AuthorDtoRequest;
import org.example.dto.BookIdResponse;
import org.example.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
@Tag(name = "Author", description = "Methods for managing authors")
public class AuthorController {
    private final AuthorService authorService;

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Creating a new author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The author was successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<BookIdResponse> createAuthor(@Valid @RequestBody AuthorDtoRequest authorDtoRequest) {
        UUID createdAuthorId = authorService.createAuthor(authorDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BookIdResponse(createdAuthorId));
    }
}
