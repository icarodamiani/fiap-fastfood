package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.OrderStatusEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderStatusRepository extends ReactiveCrudRepository<OrderStatusEntity, Long> {
}
