package io.fiap.fastfood.driven.infrastructure.core.exception.domain.model;

// CashDesk? BillingDay?
public record Till(
    Long id,
    String openAt,
    String closedAt,
    String salesPointId) {
}
