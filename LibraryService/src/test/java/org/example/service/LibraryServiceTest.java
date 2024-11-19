package org.example.service;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.LibraryBook;
import org.example.exception.NotFoundException;
import org.example.exception.RepeatedDataException;
import org.example.mapper.LibraryBookMapper;
import org.example.repository.LibraryBookRepository;
import org.example.service.impl.LibraryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.TestUtils.availableBook;
import static org.example.utils.TestUtils.bookDtoRequest;
import static org.example.utils.TestUtils.bookDtoResponse;
import static org.example.utils.TestUtils.unavailableBookDtoResponse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LibraryServiceTest {
    @Mock
    LibraryBookRepository libraryBookRepository;

    @Mock
    LibraryBookMapper libraryBookMapper;

    @InjectMocks
    LibraryServiceImpl libraryService;

    private UUID bookId;

    @BeforeEach
    void setUp() {
        bookId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test saving the book with correct data")
    void saveNewBookTest() {
        LibraryBook libraryBook = availableBook(bookId);

        when(libraryBookRepository.existsByBookId(bookId)).thenReturn(false);
        when(libraryBookRepository.save(any(LibraryBook.class))).thenReturn(libraryBook);

        libraryService.saveNewBook(bookId);
    }

    @Test
    @DisplayName("Test getting all available books")
    void getAllAvailableBooksTest() {
        int page = 1;
        int limit = 10;
        int totalPageAmount = 1;
        List<LibraryBook> availableBooks = List.of(availableBook(bookId));
        Page<LibraryBook> pageResponse = new PageImpl<>(availableBooks);

        when(libraryBookRepository.findByIsAvailableTrue(any(Pageable.class))).thenReturn(pageResponse);
        when(libraryBookMapper.mapEntityToResponse(availableBooks.getFirst(), totalPageAmount))
                .thenReturn(bookDtoResponse(bookId));

        List<BookDtoResponse> actualBookDtoResponseList = libraryService.getAllAvailableBooks(page, limit);

        assertThat(actualBookDtoResponseList).usingRecursiveComparison().isEqualTo(List.of(bookDtoResponse(bookId)));
    }

    @Test
    @DisplayName("Test updating the book with correct data")
    void updateBookTest() {
        UUID libraryBookId = UUID.randomUUID();
        BookDtoRequest bookDtoRequest = bookDtoRequest();
        LibraryBook libraryBook = LibraryBook.builder().bookId(bookId)
                .libraryBookId(libraryBookId)
                .isAvailable(false)
                .checkoutDate(LocalDateTime.of(2024, 10, 31, 12, 32))
                .returnDate(LocalDateTime.of(2024, 11, 30, 12, 32)).build();
        BookDtoResponse expectedBookDtoResponse = unavailableBookDtoResponse(bookId);

        when(libraryBookRepository.findById(libraryBookId)).thenReturn(Optional.of(libraryBook));
        when(libraryBookRepository.existsById(libraryBookId)).thenReturn(true);
        when(libraryBookMapper.mapRequestToEntity(bookDtoRequest)).thenReturn(libraryBook);
        when(libraryBookRepository.save(any(LibraryBook.class))).thenReturn(libraryBook);
        when(libraryBookMapper.mapEntityToResponse(libraryBook)).thenReturn(expectedBookDtoResponse);

        BookDtoResponse actualBookDtoResponse = libraryService.updateBook(libraryBookId, bookDtoRequest);

        assertThat(actualBookDtoResponse).usingRecursiveComparison().isEqualTo(expectedBookDtoResponse);
    }

    @Test
    @DisplayName("Test saving the book, that already exists")
    void saveExistedBookTest() {
        when(libraryBookRepository.existsByBookId(bookId)).thenReturn(true);

        Exception exception = assertThrows(RepeatedDataException.class,
                () -> libraryService.saveNewBook(bookId));

        assertEquals("Book with id " + bookId + " already exists", exception.getMessage());
        verify(libraryBookRepository, never()).save(any(LibraryBook.class));
    }

    @Test
    @DisplayName("Test updating the book, that doesn't exist")
    public void updateNotFoundBookTest() {
        when(libraryBookRepository.existsById(bookId)).thenReturn(false);

        Exception exception = assertThrows(NotFoundException.class,
                () -> libraryService.updateBook(bookId, bookDtoRequest()));

        assertEquals("Book with id " + bookId + " not found in library", exception.getMessage());
        verify(libraryBookRepository, never()).save(any(LibraryBook.class));
    }
}
