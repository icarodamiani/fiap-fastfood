package io.fiap.fastfood.core.domain.model;

public record OrderItem(
    Long id,
    String orderId,
    String productId,
    String amount,
    String quote) {
}
