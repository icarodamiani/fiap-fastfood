package io.fiap.fastfood.driver.controller.dto;

import java.util.List;

public record SalesPointDTO(
        Long id,
        String description,
        List<TillDTO> tills
) {
}
