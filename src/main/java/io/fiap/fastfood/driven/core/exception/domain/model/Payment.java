package io.fiap.fastfood.driven.core.exception.domain.model;


public record Payment(
    Long id,
    String customerId,
    String paymentId,
    String salesPointId,
    String createdAt,
    String number) {
}
