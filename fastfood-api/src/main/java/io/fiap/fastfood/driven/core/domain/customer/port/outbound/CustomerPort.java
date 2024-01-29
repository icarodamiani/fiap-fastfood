package io.fiap.fastfood.driven.core.domain.customer.port.outbound;

import io.fiap.fastfood.driven.core.domain.model.Customer;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerPort {
    Mono<Customer> createCustomer(Customer product);
    Mono<Customer> updateCustomer(String id, String operations);
    Mono<Void> deleteCustomer(String id);
    Flux<Customer> listCustomer(Pageable pageable);
}
