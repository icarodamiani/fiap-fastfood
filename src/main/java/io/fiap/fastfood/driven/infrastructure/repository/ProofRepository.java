package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.ProofEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProofRepository extends ReactiveCrudRepository<ProofEntity, Long> {
}
