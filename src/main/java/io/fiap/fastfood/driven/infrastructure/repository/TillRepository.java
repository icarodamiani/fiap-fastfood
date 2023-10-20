package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.TillEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TillRepository extends ReactiveCrudRepository<TillEntity, Long> {
}
