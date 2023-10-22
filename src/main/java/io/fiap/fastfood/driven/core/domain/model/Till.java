package io.fiap.fastfood.driven.core.domain.model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

// CashDesk? BillingDay?
public record Till(
        ZonedDateTime openAt,
        ZonedDateTime closedAt) {
    @Override
    public ZonedDateTime openAt() {
        return openAt;
    }

    @Override
    public ZonedDateTime closedAt() {
        return closedAt;
    }
}
