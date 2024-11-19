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
import org.example.dto.BookIdResponse;
import org.example.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/api/books")
@RequiredArgsConstructor
@Tag(name = "Book", description = "Methods for managing books")
public class BookController {
    private final BookService bookService;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Getting all books")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All the books were successfully sent"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<List<BookDtoResponse>> getAllBooks(
            @RequestParam(defaultValue = "1") @Min(value = 1, message = "Incorrect page. Must be greater than 1") int page,
            @RequestParam(defaultValue = "10") @Min(value = 1, message = "Incorrect data. Must be greater than 1") int limit
    ) {
        List<BookDtoResponse> bookDtoResponseList = bookService.getAllBooks(page, limit);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtoResponseList);
    }

    @GetMapping(value = "/{bookId}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Getting the book by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The book was successfully sent"),
            @ApiResponse(responseCode = "404", description = "Book with specified id wasn't found"),
            @ApiResponse(responseCode = "400", description = "Validation failed")
    })
    public ResponseEntity<BookDtoResponse> getBookById(
            @PathVariable UUID bookId
    ) {
        BookDtoResponse bookDtoResponse = bookService.getBookById(bookId);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtoResponse);
    }

    @GetMapping(value = "/isbn/{isbn}", produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Getting the book by its isbn")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The book was successfully sent"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Book with specified isbn wasn't found")
    })
    public ResponseEntity<BookDtoResponse> getBookByIsbn(
            @PathVariable String isbn
    ) {
        BookDtoResponse bookDtoResponse = bookService.getBookByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(bookDtoResponse);
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Creating a new book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The book was successfully created"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Author with specified id wasn't found"),
            @ApiResponse(responseCode = "409", description = "Book with specified isbn already exists")
    })
    public ResponseEntity<BookIdResponse> createBook(@Valid @RequestBody BookDtoRequest bookDtoRequest) {
        UUID createdBookId = bookService.createBook(bookDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new BookIdResponse(createdBookId));
    }

    @PutMapping(value = "/{bookId}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @Operation(summary = "Updating the book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The book was successfully updated"),
            @ApiResponse(responseCode = "400", description = "Validation failed"),
            @ApiResponse(responseCode = "404", description = "Book or author with specified id wasn't found")
    })
    public ResponseEntity<BookDtoResponse> updateBook(@PathVariable UUID bookId,
                                                      @Valid @RequestBody BookDtoRequest bookDtoRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(bookService.updateBook(bookId, bookDtoRequest));
    }

    @DeleteMapping(value = "/{bookId}")
    @Operation(summary = "Deleting the book")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "The book was successfully deleted"),
            @ApiResponse(responseCode = "404", description = "Book with specified id wasn't found")
    })
    public ResponseEntity<Void> deleteBook(@PathVariable UUID bookId) {
        bookService.deleteBook(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
