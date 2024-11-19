package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.service.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Validated
@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
@Tag(name = "Library", description = "Methods for managing books in the library")
public class LibraryController {
    private final LibraryService libraryService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Getting all available books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All the books were successfully sent"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<List<BookDtoResponse>> getAllAvailableBooks(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "Incorrect page. Must be greater than 1") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Incorrect data. Must be greater than 1") int limit
    ) {
        List<BookDtoResponse> bookDtoResponseList = libraryService.getAllAvailableBooks(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtoResponseList);
    }

    @PutMapping(value = "/{bookId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Updating the book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The book was successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<BookDtoResponse> updateBook(@PathVariable UUID bookId,
                                                      @Valid @RequestBody BookDtoRequest bookDtoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(libraryService.updateBook(bookId, bookDtoRequest));
    }
}
