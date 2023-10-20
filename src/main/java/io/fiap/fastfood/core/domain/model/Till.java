package io.fiap.fastfood.core.domain.model;

// CashDesk? BillingDay?
public record Till(
    Long id,
    String openAt,
    String closedAt,
    String salesPointId) {
}
