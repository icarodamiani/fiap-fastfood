package io.fiap.fastfood.driven.infrastructure.core.exception.domain.model;

public record Customer(
    Long id,
    String name,
    String identity,
    String email,
    String number) {
}

