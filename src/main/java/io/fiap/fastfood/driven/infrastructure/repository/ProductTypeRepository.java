package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.driven.infrastructure.core.entity.ProductTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductTypeRepository extends ReactiveCrudRepository<ProductTypeEntity, Long> {

}
