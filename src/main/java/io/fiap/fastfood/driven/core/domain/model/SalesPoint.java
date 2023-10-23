package io.fiap.fastfood.driven.core.domain.model;

import java.util.List;

public record SalesPoint(
        String id,
        String description,
        List<Till> tills) {
    @Override
    public String id() {
        return id;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public List<Till> tills() {
        return tills;
    }
}
