package org.example.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
@Schema(description = "Entity for getting data about the author")
public record AuthorDtoRequest(
        @NotNull
        @Schema(description = "Author's first name", example = "Charles")
        String firstName,

        @NotNull
        @Schema(description = "Author's last name", example = "Dodgson")
        String lastName,

        @Schema(description = "Author's pen name", example = "Lewis Carroll")
        String penName
) {
}
