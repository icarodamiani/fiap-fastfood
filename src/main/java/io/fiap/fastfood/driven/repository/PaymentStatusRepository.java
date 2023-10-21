package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.PaymentStatusEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface PaymentStatusRepository extends ReactiveCrudRepository<PaymentStatusEntity, Long> {
}
