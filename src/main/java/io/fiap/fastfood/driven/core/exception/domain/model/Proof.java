package io.fiap.fastfood.driven.core.exception.domain.model;

public record Proof(
    Long id,
    String orderId,
    String emittedAt,
    String number) {
}
