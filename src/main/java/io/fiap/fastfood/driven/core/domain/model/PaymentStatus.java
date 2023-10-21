package io.fiap.fastfood.driven.core.domain.model;

public record PaymentStatus(
    Long id,
    String paymentId,
    String description) {
}
