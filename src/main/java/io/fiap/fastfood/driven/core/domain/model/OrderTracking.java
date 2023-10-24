package io.fiap.fastfood.driven.core.domain.model;

import java.time.LocalDateTime;

public record OrderTracking(
        String id,
        String orderId,
        String orderNumber,
        String orderStatus,
        String role,
        LocalDateTime orderDateTime

) {
    @Override
    public String id() {
        return id;
    }

    @Override
    public String orderId() {
        return orderId;
    }

    @Override
    public String orderNumber() {
        return orderNumber;
    }

    @Override
    public String orderStatus() {
        return orderStatus;
    }

    @Override
    public String role() {
        return role;
    }

    @Override
    public LocalDateTime orderDateTime() {
        return orderDateTime;
    }
}
