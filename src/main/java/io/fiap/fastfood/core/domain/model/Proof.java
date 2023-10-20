package io.fiap.fastfood.core.domain.model;

public record Proof(
    Long id,
    String orderId,
    String emittedAt,
    String number) {
}
