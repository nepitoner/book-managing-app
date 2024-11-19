package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.LibraryBook;
import org.example.exception.NotFoundException;
import org.example.exception.RepeatedDataException;
import org.example.mapper.LibraryBookMapper;
import org.example.repository.LibraryBookRepository;
import org.example.service.LibraryService;
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
public class LibraryServiceImpl implements LibraryService {
    private final LibraryBookRepository libraryBookRepository;

    private final LibraryBookMapper libraryBookMapper;

    @Override
    public void saveNewBook(UUID bookId) {
        if (libraryBookRepository.existsByBookId(bookId)) {
            throw new RepeatedDataException("Book with id " + bookId + " already exists");
        }

        LibraryBook libraryBook = libraryBookRepository.save(LibraryBook.builder()
                .bookId(bookId)
                .isAvailable(true)
                .build());
        log.info("Book with id {} was successfully saved. Library id {}",
                bookId, libraryBook.getLibraryBookId());
    }

    @Override
    public List<BookDtoResponse> getAllAvailableBooks(int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<LibraryBook> responsePage = libraryBookRepository.findByIsAvailableTrue(pageable);
        List<LibraryBook> availableBooks = responsePage.getContent();
        int totalPageAmount = responsePage.getTotalPages();

        log.info("Getting available books. Total page amount {}", totalPageAmount);
        return availableBooks.stream().map(
                availableBook -> libraryBookMapper.mapEntityToResponse(availableBook, totalPageAmount)
        ).toList();
    }

    @Override
    @Transactional
    public BookDtoResponse updateBook(UUID libraryBookId, BookDtoRequest bookDtoRequest) {
        if (!libraryBookRepository.existsById(libraryBookId)) {
            throw new NotFoundException("Book with id " + libraryBookId + " not found in library");
        }

        LibraryBook savedLibraryBook = libraryBookRepository.findById(libraryBookId).orElseThrow();
        LibraryBook libraryBook = libraryBookMapper.mapRequestToEntity(bookDtoRequest);
        libraryBook.setLibraryBookId(libraryBookId);
        libraryBook.setBookId(savedLibraryBook.getBookId());
        LibraryBook updatedlibraryBook = libraryBookRepository.save(libraryBook);
        log.info("Book in library with id {} was successfully updated", libraryBookId);
        return libraryBookMapper.mapEntityToResponse(updatedlibraryBook);
    }
}
