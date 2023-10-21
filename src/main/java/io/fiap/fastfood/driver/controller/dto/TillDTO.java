package io.fiap.fastfood.driver.controller.dto;

import java.time.LocalDateTime;

public record TillDTO(
        Long id,
        LocalDateTime openAt,
        LocalDateTime closedAt
) {
}
