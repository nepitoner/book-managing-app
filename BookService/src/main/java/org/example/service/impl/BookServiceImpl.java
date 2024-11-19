package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.Author;
import org.example.entity.Book;
import org.example.entity.Outbox;
import org.example.exception.NotFoundException;
import org.example.exception.RepeatedDataException;
import org.example.mapper.BookMapper;
import org.example.repository.AuthorRepository;
import org.example.repository.BookRepository;
import org.example.repository.OutboxRepository;
import org.example.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;

    private final BookMapper bookMapper;

    private final AuthorRepository authorRepository;

    private final OutboxRepository outboxRepository;

    @Override
    public List<BookDtoResponse> getAllBooks(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<Book> responsePage = bookRepository.findAll(pageable);
        List<Book> books = responsePage.getContent();
        int totalPageAmount = responsePage.getTotalPages();
        log.info("Getting all books. Total page amount {}", totalPageAmount);
        return books.stream().map(book -> bookMapper.mapEntityToResponse(book, totalPageAmount)).toList();
    }

    @Override
    public BookDtoResponse getBookById(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
        log.info("Book with id {} was successfully found", bookId);
        return bookMapper.mapEntityToResponse(book);
    }

    @Override
    public BookDtoResponse getBookByIsbn(String isbn) {
        Book book = bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new NotFoundException("Book with isbn " + isbn + " not found"));
        log.info("Book with isbn {} was successfully found", isbn);
        return bookMapper.mapEntityToResponse(book);
    }

    @Override
    @Transactional
    public UUID createBook(BookDtoRequest bookDtoRequest) {
        isBookDataCorrect(bookDtoRequest);
        Author author = authorRepository.findById(bookDtoRequest.authorId()).orElseThrow();
        UUID bookId = bookRepository.save(bookMapper.mapRequestToEntity(bookDtoRequest, author)).getBookId();
        log.info("Book with {} was successfully created. New id {}", bookDtoRequest.isbn(), bookId);

        outboxRepository.save(Outbox.builder().bookId(bookId).build());
        log.info("Book with {} was successfully stored to outbox", bookId);
        return bookId;
    }

    @Override
    @Transactional
    public BookDtoResponse updateBook(UUID bookId, BookDtoRequest bookDtoRequest) {
        if (bookRepository.existsById(bookId)) {
            Author author = authorRepository.findById(bookDtoRequest.authorId())
                    .orElseThrow(() -> new NotFoundException("Author with id " + bookDtoRequest.authorId() + " not found"));
            Book book = bookMapper.mapRequestToEntity(bookDtoRequest, author);
            book.setBookId(bookId);
            Book updatedBook = bookRepository.save(book);
            log.info("Book with id {} was successfully updated", bookId);
            return bookMapper.mapEntityToResponse(updatedBook);
        } else {
            throw new NotFoundException("Book with id " + bookId + " not found");
        }
    }

    @Override
    @Transactional
    public void deleteBook(UUID bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new NotFoundException("Book with id " + bookId + " not found"));
        bookRepository.delete(book);
        log.info("Book with id {} was successfully deleted", bookId);
    }

    private void isBookDataCorrect(BookDtoRequest bookDtoRequest) {
        if (bookRepository.findByIsbn(bookDtoRequest.isbn()).isPresent()) {
            throw new RepeatedDataException("Book with isbn " + bookDtoRequest.isbn() + " already exists");
        }
        if (!authorRepository.existsById(bookDtoRequest.authorId())) {
            throw new NotFoundException("Author with id " + bookDtoRequest.authorId() + " not found");
        }
    }
}
