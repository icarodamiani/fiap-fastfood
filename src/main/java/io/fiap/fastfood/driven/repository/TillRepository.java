package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.TillEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface TillRepository extends ReactiveCrudRepository<TillEntity, String> {
}
