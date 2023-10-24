package io.fiap.fastfood.driven.core.exception.domain.model;

public record PaymentStatus(
    Long id,
    Long paymentId,
    String description) {
}
