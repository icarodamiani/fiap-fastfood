package io.fiap.fastfood.driven.core.exception.domain.model;

import java.math.BigDecimal;
import java.util.Date;

import io.fiap.fastfood.driver.controller.dto.ProofDTO;

public record Payment(
    Long id,
    String method,
    BigDecimal amount,
    Date date,
    ProofDTO proofId) {
}
