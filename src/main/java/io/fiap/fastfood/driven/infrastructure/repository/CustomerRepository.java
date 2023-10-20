package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {
}
