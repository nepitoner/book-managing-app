package org.example.service;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Outbox;
import org.example.exception.NotFoundException;
import org.example.mapper.BookMapper;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.OutboxRepository;
import org.example.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.example.utils.TestUtils.book;
import static org.example.utils.TestUtils.bookDtoRequest;
import static org.example.utils.TestUtils.bookDtoResponse;
import static org.example.utils.TestUtils.expectedAuthor;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    BookRepository bookRepository;

    @Mock
    BookMapper bookMapper;

    @Mock
    AuthorRepository authorRepository;

    @Mock
    OutboxRepository outboxRepository;

    @InjectMocks
    BookServiceImpl bookService;

    private UUID authorId;
    private UUID bookId;

    @BeforeEach
    void setUp() {
        authorId = UUID.randomUUID();
        bookId = UUID.randomUUID();
    }

    @Test
    @DisplayName("Test getting all books")
    void getAllBooksTest() {
        int limit = 1;
        int page = 1;
        int totalAmount = 1;
        Book book = book(bookId, authorId);
        Page<Book> bookPage = new PageImpl<>(List.of(book),
                PageRequest.of(0, limit), totalAmount);
        Pageable pageable = PageRequest.of(0, limit);
        List<BookDtoResponse> expectedBookDtoResponseList = List.of(bookDtoResponse(bookId));

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.mapEntityToResponse(book, totalAmount)).thenReturn(expectedBookDtoResponseList.getFirst());

        List<BookDtoResponse> actualBookDtoResponseList = bookService.getAllBooks(page, limit);

        assertEquals(expectedBookDtoResponseList.size(), actualBookDtoResponseList.size());
        assertThat(actualBookDtoResponseList).usingRecursiveAssertion().isEqualTo(expectedBookDtoResponseList);
    }

    @Test
    @DisplayName("Test getting the book by correct id")
    void getBookByIdTest() {
        Book book = book(bookId, authorId);
        BookDtoResponse expectedBookDtoResponse = bookDtoResponse(bookId);

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.mapEntityToResponse(book)).thenReturn(expectedBookDtoResponse);

        BookDtoResponse actualBookDtoResponse = bookService.getBookById(bookId);

        assertThat(actualBookDtoResponse).usingRecursiveAssertion().isEqualTo(expectedBookDtoResponse);
    }

    @Test
    @DisplayName("Test getting the book by correct isbn")
    void getBookByIsbnTest() {
        Book book = book(bookId, authorId);
        String isbn = book.getIsbn();
        BookDtoResponse expectedBookDtoResponse = bookDtoResponse(bookId);

        when(bookRepository.findByIsbn(isbn)).thenReturn(Optional.of(book));
        when(bookMapper.mapEntityToResponse(book)).thenReturn(expectedBookDtoResponse);

        BookDtoResponse actualBookDtoResponse = bookService.getBookByIsbn(isbn);

        assertThat(actualBookDtoResponse).usingRecursiveAssertion().isEqualTo(expectedBookDtoResponse);
    }

    @Test
    @DisplayName("Test creating the book with correct data")
    void createBookTest() {
        Book book = book(bookId, authorId);
        Author expectedAuthor =  expectedAuthor(authorId);
        BookDtoRequest bookDtoRequest = bookDtoRequest(authorId);
        Outbox outbox = Outbox.builder().bookId(bookId).build();

        when(bookRepository.findByIsbn(book.getIsbn())).thenReturn(Optional.empty());
        when(authorRepository.existsById(authorId)).thenReturn(true);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(expectedAuthor));
        when(bookMapper.mapRequestToEntity(bookDtoRequest, expectedAuthor)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(outboxRepository.save(any(Outbox.class))).thenReturn(outbox);

        UUID actualBookId = bookService.createBook(bookDtoRequest);

        assertEquals(bookId, actualBookId);
    }

    @Test
    @DisplayName("Test updating the book with correct data")
    void updateBookTest() {
        Book book = book(bookId, authorId);
        BookDtoRequest bookDtoRequest = bookDtoRequest(authorId);
        Author expectedAuthor =  expectedAuthor(authorId);
        BookDtoResponse expectedBookDtoResponse = bookDtoResponse(bookId);

        when(bookRepository.existsById(bookId)).thenReturn(true);
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(expectedAuthor));
        when(bookMapper.mapRequestToEntity(bookDtoRequest, expectedAuthor)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.mapEntityToResponse(book)).thenReturn(expectedBookDtoResponse);

        BookDtoResponse actualBookDtoResponse = bookService.updateBook(bookId, bookDtoRequest);

        assertThat(actualBookDtoResponse).usingRecursiveAssertion().isEqualTo(expectedBookDtoResponse);
    }

    @Test
    @DisplayName("Test deleting the book by correct id")
    void deleteBookTest() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book(bookId, authorId)));

        bookService.deleteBook(bookId);
    }

    @Test
    @DisplayName("Test getting the book by incorrect id")
    public void getBookByIncorrectIdTest() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookService.getBookById(bookId));

        assertEquals("Book with id " + bookId + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Test getting the book by incorrect isbn")
    public void getBookByIncorrectIsbnTest() {
        String incorrectIsbn = "";
        when(bookRepository.findByIsbn(incorrectIsbn)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookService.getBookByIsbn(incorrectIsbn));

        assertEquals("Book with isbn " + incorrectIsbn + " not found", exception.getMessage());
    }

    @Test
    @DisplayName("Test creating a new book with incorrect author")
    public void createBookWithIncorrectAuthorTest() {
        BookDtoRequest bookDtoRequest = bookDtoRequest(null);

        assertThrows(IllegalArgumentException.class,
                () -> bookService.createBook(bookDtoRequest));
    }

    @Test
    @DisplayName("Test updating the book with incorrect id")
    public void updateBookWithIncorrectIdTest() {
        when(bookRepository.existsById(bookId)).thenReturn(false);

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookService.updateBook(bookId, bookDtoRequest(UUID.randomUUID())));

        assertEquals("Book with id " + bookId + " not found", exception.getMessage());
    }

    @Test
    public void deleteBookWithIncorrectIdTest() {
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        NotFoundException exception = assertThrows(NotFoundException.class,
                () -> bookService.deleteBook(bookId));

        assertEquals("Book with id " + bookId + " not found", exception.getMessage());
    }
}
