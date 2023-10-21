package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.CustomerEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {
}
