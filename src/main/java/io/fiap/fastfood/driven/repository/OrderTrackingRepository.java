package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.OrderTrackingEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderTrackingRepository extends ReactiveCrudRepository<OrderTrackingEntity, String> {
    Flux<OrderTrackingEntity> findByOrderIdOrderByOrderDateTime(String orderId);

    Flux<OrderTrackingEntity> findByOrderStatusNot(String status, Pageable pageable);

}
