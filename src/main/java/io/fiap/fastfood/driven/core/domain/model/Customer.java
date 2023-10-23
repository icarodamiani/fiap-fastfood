package io.fiap.fastfood.driven.core.domain.model;

public record Customer(
    Long id,
    String name,
    String identity,
    String email,
    String number) {
}

