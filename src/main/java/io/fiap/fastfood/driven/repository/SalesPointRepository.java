package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.SalesPointEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface SalesPointRepository extends ReactiveCrudRepository<SalesPointEntity, String> {
}
