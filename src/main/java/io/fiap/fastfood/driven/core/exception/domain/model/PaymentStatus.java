package io.fiap.fastfood.driven.core.exception.domain.model;

public record PaymentStatus(
    Long id,
    String paymentId,
    String description) {
}
