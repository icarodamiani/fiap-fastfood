package io.fiap.fastfood.core.domain.model;


public record Order(
    Long id,
    String customerId,
    String paymentId, // accountId?
    String salesPointId,
    String createdAt,
    String number) {
}
