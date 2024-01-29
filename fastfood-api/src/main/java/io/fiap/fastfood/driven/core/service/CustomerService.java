package io.fiap.fastfood.driven.core.service;

import io.fiap.fastfood.driven.core.domain.customer.port.inbound.CustomerUseCase;
import io.fiap.fastfood.driven.core.domain.customer.port.outbound.CustomerPort;
import io.fiap.fastfood.driven.core.domain.model.Customer;
import io.fiap.fastfood.driven.core.exception.BadRequestException;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService implements CustomerUseCase {

    private final CustomerPort productPort;


    public CustomerService(CustomerPort productPort) {
        this.productPort = productPort;
    }

    @Override
    public Mono<Customer> create(Customer product) {
        return Mono.just(product)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(productPort::createCustomer);
    }

    @Override
    public Flux<Customer> list(Pageable pageable) {
        return productPort.listCustomer(pageable);
    }

    @Override
    public Mono<Void> delete(String id) {
        return Mono.just(id)
            .switchIfEmpty(Mono.defer(() -> Mono.error(new BadRequestException())))
            .flatMap(productPort::deleteCustomer);
    }


    @Override
    public Mono<Customer> update(String id, String operations) {
        return productPort.updateCustomer(id, operations);
    }

}
