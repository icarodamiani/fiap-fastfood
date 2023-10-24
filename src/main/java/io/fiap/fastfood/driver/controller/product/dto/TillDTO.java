package io.fiap.fastfood.driver.controller.product.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

public record TillDTO(
        @Schema(example = "2023-10-21T09:00:44.495Z")
        String openAt,
        @Schema(example = "2023-10-21T09:00:44.495Z")
        String closedAt) {
    @Override
    public String openAt() {
        return openAt;
    }

    @Override
    public String closedAt() {
        return closedAt;
    }
}
