package io.fiap.fastfood.driven.repository;

import io.fiap.fastfood.driven.core.entity.CustomerEntity;
import java.util.Date;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface CustomerRepository extends ReactiveCrudRepository<CustomerEntity, Long> {

    Flux<CustomerEntity> findByIdentity(String identity, Pageable pageable);

    Flux<CustomerEntity> findByIdNotNull(Pageable pageable);

}
