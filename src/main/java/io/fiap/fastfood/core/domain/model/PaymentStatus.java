package io.fiap.fastfood.core.domain.model;

public record PaymentStatus(
    Long id,
    String paymentId,
    String description) {
}
