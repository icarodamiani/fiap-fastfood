package io.fiap.fastfood.driven.core.domain.model;

public record OrderItem(
    Long id,
    String orderId,
    String productId,
    String amount,
    String quote) {
}
