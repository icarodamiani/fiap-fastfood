package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.OrderEntity;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface OrderRepository extends ReactiveCrudRepository<OrderEntity, Long> {

    Flux<OrderEntity> findById(Long id, Pageable pageable);

    Flux<OrderEntity> findByIdNotNull(Pageable pageable);

}
