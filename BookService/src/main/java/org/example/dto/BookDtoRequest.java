package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Entity for getting data about the book")
public record BookDtoRequest(
        @NotNull
        @Pattern(regexp = "^(?:ISBN(?:-13)?:? )?(978|979)[- ]?([0-9]{1,5})[- ]?([0-9]+)[- ]?([0-9]+)[- ]?([0-9X])$",
        message = "Incorrect isbn")
        @Schema(description = "Book's isbn", example = "978-0-306-40615-7")
        String isbn,

        @NotNull
        @Schema(description = "Book's title", example = "Alice in wonderland")
        String title,

        @NotNull
        @Schema(description = "Book's genre", example = "Fantasy")
        String genre,

        @NotNull
        @Schema(description = "Book's description", example = "Book about a little girl in another world")
        String description,

        @NotNull
        @Schema(description = "Book's author's id", example = "71f5135e-fb46-415c-b4cf-bbb9be5692d")
        UUID authorId
) {
}
