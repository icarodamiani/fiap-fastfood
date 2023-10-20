package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.OrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, Long> {
}
