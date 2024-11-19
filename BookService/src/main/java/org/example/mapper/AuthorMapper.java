package org.example.mapper;

import org.example.dto.AuthorDtoRequest;
import org.example.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {
    @Mapping(target = "authorId", ignore = true)
    Author mapRequestToEntity(AuthorDtoRequest authorDtoRequest);
}
