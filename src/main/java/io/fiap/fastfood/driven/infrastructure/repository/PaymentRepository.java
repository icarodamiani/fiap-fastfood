package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.PaymentEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentRepository extends ReactiveCrudRepository<PaymentEntity, Long> {
}
