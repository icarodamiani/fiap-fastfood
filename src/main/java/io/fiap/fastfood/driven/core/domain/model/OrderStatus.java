package io.fiap.fastfood.driven.core.domain.model;

public record OrderStatus(
    Long id,
    String name,
    String description,
    Boolean customerFriendly) {
}
