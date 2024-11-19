package org.example.service;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;

import java.util.List;
import java.util.UUID;

public interface BookService {
    List<BookDtoResponse> getAllBooks(int page, int limit);

    BookDtoResponse getBookById(UUID bookId);

    BookDtoResponse getBookByIsbn(String isbn);

    UUID createBook(BookDtoRequest bookDtoRequest);

    BookDtoResponse updateBook(UUID bookId, BookDtoRequest bookDtoRequest);

    void deleteBook(UUID bookId);
}
