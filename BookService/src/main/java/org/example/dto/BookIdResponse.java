package org.example.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record BookIdResponse(
        UUID id
) {
}
