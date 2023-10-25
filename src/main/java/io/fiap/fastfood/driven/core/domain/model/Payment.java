package io.fiap.fastfood.driven.core.domain.model;

import java.math.BigDecimal;
import java.util.Date;


public record Payment(
    Long id,
    String method,
    BigDecimal amount,
    Date date,
    Long orderId) {
}
