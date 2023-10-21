package io.fiap.fastfood.driven.core.domain.model;

import java.util.List;

public record SalesPoint(
        Long id,
        String description,
        List<Till> tills) {
}
