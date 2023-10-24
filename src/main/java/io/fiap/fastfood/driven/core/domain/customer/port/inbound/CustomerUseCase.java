package io.fiap.fastfood.driven.core.domain.customer.port.inbound;

import io.fiap.fastfood.driven.core.domain.model.Customer;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerUseCase {
    Mono<Customer> create(Customer value);
    Mono<Void> delete(Long id);
    Mono<Customer> update(Long id, String operations);
    Flux<Customer> list(Pageable pageable);
}
