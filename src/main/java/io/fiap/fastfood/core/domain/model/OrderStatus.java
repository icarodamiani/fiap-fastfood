package io.fiap.fastfood.core.domain.model;

public record OrderStatus(
    Long id,
    String name,
    String description,
    Boolean customerFriendly) {
}
