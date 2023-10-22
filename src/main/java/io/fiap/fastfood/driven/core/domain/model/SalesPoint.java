package io.fiap.fastfood.driven.core.domain.model;

import java.util.List;

public record SalesPoint(
        String id,
        String description,
        List<Till> tills) {
    public SalesPoint {
    }
}
