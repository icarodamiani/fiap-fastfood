package io.fiap.fastfood.driven.core.exception.domain.model;

public record OrderItem(
    Long id,
    Long productId,
    Long amount,
    String quote) {
}
