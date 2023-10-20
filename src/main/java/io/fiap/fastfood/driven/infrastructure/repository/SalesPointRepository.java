package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.SalesPointEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SalesPointRepository extends ReactiveCrudRepository<SalesPointEntity, Long> {
}
