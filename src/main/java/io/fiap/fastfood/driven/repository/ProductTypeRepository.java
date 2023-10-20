package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.ProductTypeEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface ProductTypeRepository extends ReactiveCrudRepository<ProductTypeEntity, Long> {

}
