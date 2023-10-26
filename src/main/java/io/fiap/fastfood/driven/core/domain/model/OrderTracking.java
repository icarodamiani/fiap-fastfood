package io.fiap.fastfood.driven.core.domain.model;

import java.time.LocalDateTime;

public record OrderTracking(
        String id,
        String orderId,
        String orderNumber,
        String orderStatus,
        String role,
        LocalDateTime orderDateTime,

        Long orderTimeSpent

) {
}
