package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.SalesPointEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface SalesPointRepository extends ReactiveCrudRepository<SalesPointEntity, String> {
    Flux<SalesPointEntity> findById(String id, Pageable pageable);

    Flux<SalesPointEntity> findByIdNotNull(Pageable pageable);
}
