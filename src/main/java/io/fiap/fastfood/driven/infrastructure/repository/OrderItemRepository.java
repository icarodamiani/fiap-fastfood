package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.OrderItemEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderItemRepository extends ReactiveCrudRepository<OrderItemEntity, Long> {
}
