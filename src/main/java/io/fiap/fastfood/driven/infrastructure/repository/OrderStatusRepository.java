package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.OrderStatusEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderStatusRepository extends ReactiveCrudRepository<OrderStatusEntity, Long> {
}
