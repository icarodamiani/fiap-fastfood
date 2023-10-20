package io.fiap.fastfood.driven.infrastructure.core.exception.domain.model;

public record OrderItem(
    Long id,
    String orderId,
    String productId,
    String amount,
    String quote) {
}
