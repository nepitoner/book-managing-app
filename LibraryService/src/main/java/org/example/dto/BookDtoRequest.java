package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@Schema(description = "Entity for updating book's status")
public record BookDtoRequest(
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
