package io.fiap.fastfood.driven.core.exception.domain.model;

import java.math.BigDecimal;
import java.util.Date;

public record Proof(
    Long id,
    Date date,
    BigDecimal number) {
}