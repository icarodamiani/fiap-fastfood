package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.driven.infrastructure.core.entity.ProductEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface ProductRepository extends ReactiveCrudRepository<ProductEntity, Long> {

    Flux<ProductEntity> findByTypeId(String typeId, Pageable pageable);

    Flux<ProductEntity> findByIdNotNull(Pageable pageable);

}
