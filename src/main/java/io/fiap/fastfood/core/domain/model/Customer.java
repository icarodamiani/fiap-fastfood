package io.fiap.fastfood.core.domain.model;

public record Customer(
    Long id,
    String name,
    String identity,
    String email,
    String number) {
}

