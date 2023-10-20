package io.fiap.fastfood.driven.infrastructure.repository;

import io.fiap.fastfood.core.entity.PaymentStatusEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentStatusRepository extends ReactiveCrudRepository<PaymentStatusEntity, Long> {
}
