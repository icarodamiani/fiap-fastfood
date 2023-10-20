package io.fiap.fastfood.driven.infrastructure.core.exception.domain.model;

public record OrderStatus(
    Long id,
    String name,
    String description,
    Boolean customerFriendly) {
}
