package io.fiap.fastfood.driven.infrastructure.core.exception.domain.model;

public record PaymentStatus(
    Long id,
    String paymentId,
    String description) {
}
