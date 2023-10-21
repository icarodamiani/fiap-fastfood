package io.fiap.fastfood.driven.core.domain.model;

public record Proof(
    Long id,
    String orderId,
    String emittedAt,
    String number) {
}
