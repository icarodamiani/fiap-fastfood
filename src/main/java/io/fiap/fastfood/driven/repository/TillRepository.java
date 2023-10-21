package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.TillEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TillRepository extends ReactiveCrudRepository<TillEntity, Long> {

    Flux<TillEntity> findByIdSalesPoint(Long id);

    Mono<Void> deleteAllByIdSalesPoint(Long id);
}
