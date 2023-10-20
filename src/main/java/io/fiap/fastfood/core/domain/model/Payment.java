package io.fiap.fastfood.core.domain.model;


public record Payment(
    Long id,
    String customerId,
    String paymentId,
    String salesPointId,
    String createdAt,
    String number) {
}
