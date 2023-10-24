package io.fiap.fastfood.driven.core.entity;

import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Objects;

public class TillEntity { // CashDesk? BillingDay?

    @Field("data_hora_abertura")
    private LocalDateTime openAt;

    @Field("data_hora_fechamento")
    private LocalDateTime closedAt;

    public TillEntity() {
    }

    public TillEntity(LocalDateTime openAt, LocalDateTime closedAt) {
        this.openAt = openAt;
        this.closedAt = closedAt;
    }

    public LocalDateTime getOpenAt() {
        return openAt;
    }

    public void setOpenAt(LocalDateTime openAt) {
        this.openAt = openAt;
    }

    public LocalDateTime getClosedAt() {
        return closedAt;
    }

    public void setClosedAt(LocalDateTime closedAt) {
        this.closedAt = closedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TillEntity that = (TillEntity) o;
        return Objects.equals(openAt, that.openAt) && Objects.equals(closedAt, that.closedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openAt, closedAt);
    }
}
