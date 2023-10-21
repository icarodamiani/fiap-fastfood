package io.fiap.fastfood.driven.core.exception.domain.model;


public record Order(
    Long id,
    String customerId,
    String paymentId, // accountId?
    String salesPointId,
    String createdAt,
    String number) {
}
