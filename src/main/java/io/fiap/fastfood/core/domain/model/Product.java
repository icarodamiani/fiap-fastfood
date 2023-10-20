package io.fiap.fastfood.core.domain.model;

import java.math.BigDecimal;
import java.util.Optional;

public record Product(
    Long id,
    String typeId,
    String description,
    BigDecimal price,
    String availability,
    ProductType type) {

    Optional<Long> getId() {
        return Optional.ofNullable(id());
    }

    Optional<ProductType> getType() {
        return Optional.ofNullable(type());
    }
}
