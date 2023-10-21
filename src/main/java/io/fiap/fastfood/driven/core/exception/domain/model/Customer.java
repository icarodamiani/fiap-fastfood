package io.fiap.fastfood.driven.core.exception.domain.model;

public record Customer(
    Long id,
    String name,
    String identity,
    String email,
    String number) {
}

