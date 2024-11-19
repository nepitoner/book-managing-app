package org.example.mapper;

import org.example.dto.BookDtoRequest;
import org.example.dto.BookDtoResponse;
import org.example.entity.Author;
import org.example.entity.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BookMapper {
    @Mapping(target = "authorPenName", source = "book.author.penName")
    @Mapping(target = "authorLastName", source = "book.author.lastName")
    @Mapping(target = "authorFirstName", source = "book.author.firstName")
    BookDtoResponse mapEntityToResponse(Book book);

    @Mapping(target = "authorPenName", source = "book.author.penName")
    @Mapping(target = "authorLastName", source = "book.author.lastName")
    @Mapping(target = "authorFirstName", source = "book.author.firstName")
    BookDtoResponse mapEntityToResponse(Book book, int totalPageAmount);

    @Mapping(target = "bookId", ignore = true)
    Book mapRequestToEntity(BookDtoRequest bookDtoRequest, Author author);
}
