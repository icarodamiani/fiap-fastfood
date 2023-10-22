package io.fiap.fastfood.driven.core.domain.model;

// CashDesk? BillingDay?
public record Till(
    Long id,
    String openAt,
    String closedAt,
    String salesPointId) {
}
