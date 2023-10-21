package io.fiap.fastfood.driver.controller.dto;

import io.fiap.fastfood.driven.core.domain.model.Till;

import java.util.List;

public record SalesPointDTO(
        Long id,
        String description,
        List<Till> tills
) {
}
