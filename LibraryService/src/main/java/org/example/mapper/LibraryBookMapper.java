package org.example.mapper;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.LibraryBook;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface LibraryBookMapper {
    @Mapping(target = "bookId", source = "book.bookId")
    @Mapping(target = "libraryBookId", source = "book.libraryBookId")
    BookDtoResponse mapEntityToResponse(LibraryBook book);

    @Mapping(target = "bookId", source = "book.bookId")
    @Mapping(target = "isAvailable", source = "book.isAvailable")
    @Mapping(target = "libraryBookId", source = "book.libraryBookId")
    BookDtoResponse mapEntityToResponse(LibraryBook book, int totalPageAmount);

    LibraryBook mapRequestToEntity(BookDtoRequest bookDtoRequests);
}
