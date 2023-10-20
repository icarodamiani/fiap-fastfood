package io.fiap.fastfood.driven.infrastructure.core.exception.domain.model;

public record Proof(
    Long id,
    String orderId,
    String emittedAt,
    String number) {
}
