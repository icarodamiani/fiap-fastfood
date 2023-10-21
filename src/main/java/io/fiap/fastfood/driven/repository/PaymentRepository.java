package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.PaymentEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentRepository extends ReactiveCrudRepository<PaymentEntity, Long> {
}
