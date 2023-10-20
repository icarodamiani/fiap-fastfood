package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.OrderEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, Long> {
}
