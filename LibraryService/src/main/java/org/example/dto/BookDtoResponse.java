package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Schema(description = "Book data")
public record BookDtoResponse(
        @NotNull
        @Schema(description = "Available book's id in library", example = "71f5135e-fb46-415c-b4cf-bbb9be5692d")
        UUID libraryBookId,

        @NotNull
        @Schema(description = "Available book's id", example = "71f5135e-fb46-415c-b4cf-bbb9be5692d")
        UUID bookId,

        @NotNull
        @Schema(description = "Book availability status", example = "true")
        boolean isAvailable,

        @Schema(description = "The date of checking out, if the book is taken; " +
                "null, if available", example = "null")
        LocalDateTime checkoutDate,

        @Schema(description = "The date when the book should be returned, if the book is taken; " +
                "null, if available", example = "null")
        LocalDateTime returnDate
) {
}
