package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "Book data")
public record BookDtoResponse(
        @NotNull
        @Schema(description = "Book's id", example = "71f5135e-fb46-415c-b4cf-bbb9be5692d")
        UUID bookId,

        @NotNull
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
        @Schema(description = "Book's author's first name", example = "Charles")
        String authorFirstName,

        @NotNull
        @Schema(description = "Book's author's last name", example = "Dodgson")
        String authorLastName,

        @Schema(description = "Book's author's pen name", example = "Lewis Carroll")
        String authorPenName
) {
}
