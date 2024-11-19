package org.example.service;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.dto.BookIdResponse;

import java.util.List;
import java.util.UUID;

public interface LibraryService {
    void saveNewBook(UUID bookId);

    List<BookDtoResponse> getAllAvailableBooks(int page, int limit);

    BookDtoResponse updateBook(UUID libraryBookId, BookDtoRequest bookDtoRequest);
}
